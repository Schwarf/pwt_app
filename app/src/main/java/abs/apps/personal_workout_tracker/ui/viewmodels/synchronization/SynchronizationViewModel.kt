package abs.apps.personal_workout_tracker.ui.viewmodels.synchronization


import abs.apps.personal_training_tracker.data.repositories.ITrainingTimestampRepository
import abs.apps.personal_workout_tracker.data.database.Synchronization
import abs.apps.personal_workout_tracker.data.database.Training
import abs.apps.personal_workout_tracker.data.database.TrainingTimestamp
import abs.apps.personal_workout_tracker.data.database.Workout
import abs.apps.personal_workout_tracker.data.database.WorkoutTimestamp
import abs.apps.personal_workout_tracker.data.http_client.DTOConverter
import abs.apps.personal_workout_tracker.data.http_client.TrainingDTO
import abs.apps.personal_workout_tracker.data.http_client.TrainingTimestampDTO
import abs.apps.personal_workout_tracker.data.http_client.WorkoutDTO
import abs.apps.personal_workout_tracker.data.http_client.WorkoutTimestampDTO
import abs.apps.personal_workout_tracker.data.repositories.ISynchronizationRepository
import abs.apps.personal_workout_tracker.data.repositories.ITrainingRepository
import abs.apps.personal_workout_tracker.data.repositories.IWorkoutRepository
import abs.apps.personal_workout_tracker.data.repositories.IWorkoutTimestampRepository
import abs.apps.personal_workout_tracker.ui.viewmodels.helpers.getBaseUrl
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SynchronizationViewModel(
    private val synchronizationRepository: ISynchronizationRepository,
    private val workoutRepository: IWorkoutRepository,
    private val trainingRepository: ITrainingRepository,
    private val workoutTimestampRepository: IWorkoutTimestampRepository,
    private val trainingTimestampRepository: ITrainingTimestampRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            try {
                val timestamp = getLatestTimestamp()
                val workouts = getWorkouts(timestamp)
                val trainings = getTrainings(timestamp)
                val workoutTimestamps = getWorkoutTimestamps(timestamp)
                val trainingTimestamps = getTrainingTimestamps(timestamp)
                convertAndSend(workouts, trainings, workoutTimestamps, trainingTimestamps)
                updateSynchronizationTimestamp()

            } catch (e: Exception) {
                Log.e("Error", "Error in SynchronisationViewModel initialization", e)
            }
        }

    }

    private suspend fun updateSynchronizationTimestamp() {
        val synchronization = Synchronization(
            id = 0,
            attempted = true,
            succeeded = true,
            timestamp = System.currentTimeMillis()
        )
        synchronizationRepository.upsertSynchronization(synchronization)
    }

    private suspend fun getLatestTimestamp(): Long {
        return withContext(Dispatchers.IO) { synchronizationRepository.getLatestSuccessfulSynchronization() };
    }

    private suspend fun getWorkouts(timestamp: Long): List<Workout> {
        return withContext(Dispatchers.IO) {
            workoutRepository.getUpdatesForSynchronization(
                timestamp
            )
        }
    }

    private suspend fun getTrainings(timestamp: Long): List<Training> {
        return withContext(Dispatchers.IO) {
            trainingRepository.getUpdatesForSynchronization(
                timestamp
            )
        }
    }

    private suspend fun getWorkoutTimestamps(timestamp: Long): List<WorkoutTimestamp> {
        return withContext(Dispatchers.IO) {
            workoutTimestampRepository.getUpdatesForSynchronization(
                timestamp
            )
        }
    }

    private suspend fun getTrainingTimestamps(timestamp: Long): List<TrainingTimestamp> {
        return withContext(Dispatchers.IO) {
            trainingTimestampRepository.getUpdatesForSynchronization(
                timestamp
            )
        }
    }

    private suspend fun <Input : Any, ConvertedOutput : Any> sendInsertionList(
        inputList: List<Input>,
        converter: DTOConverter<Input, ConvertedOutput>,
        url: String,
        jsonName: String
    ) {
        Log.d("URL", url)
        val listDTO = inputList.map { item -> converter.convert(item) }
        val wrapper = mapOf(jsonName to listDTO)
        val gson = Gson()
        val jsonString = gson.toJson(wrapper)
        Log.d("JSON-STRING", jsonString)
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
        withContext(Dispatchers.IO) {
            Fuel.post(url).jsonBody(jsonString).response { _, _, result ->
                when (result) {
                    is Result.Failure -> {
                        val ex = result.getException()
                        println("Error: $ex")
                    }

                    is Result.Success -> {
                        val data = result.get()
                        println("Response: ${String(data)}")
                    }

                }
            }
        }
    }

    private suspend fun convertAndSend(
        workouts: List<Workout>, trainings: List<Training>,
        workoutTimestamps: List<WorkoutTimestamp>, trainingTimestamps: List<TrainingTimestamp>
    ) {
        if (workouts.isNotEmpty()) {
            val name = "workouts"
            val workoutConverter = DTOConverter(Workout::class, WorkoutDTO::class)
            sendInsertionList(workouts, workoutConverter, getBaseUrl(name), name)
        }
        if (trainings.isNotEmpty()) {
            val name = "trainings"
            val trainingConverter = DTOConverter(Training::class, TrainingDTO::class)
            sendInsertionList(trainings, trainingConverter, getBaseUrl(name), name)
        }
        if (workoutTimestamps.isNotEmpty()) {
            val name = "workout_timestamps"
            val workoutTimestampConverter =
                DTOConverter(WorkoutTimestamp::class, WorkoutTimestampDTO::class)
            sendInsertionList(workoutTimestamps, workoutTimestampConverter, getBaseUrl(name), name)
        }
        if (trainingTimestamps.isNotEmpty()) {
            val name = "training_timestamps"
            val trainingTimestampConverter =
                DTOConverter(TrainingTimestamp::class, TrainingTimestampDTO::class)
            sendInsertionList(
                trainingTimestamps,
                trainingTimestampConverter,
                getBaseUrl(name),
                name
            )
        }

    }
}