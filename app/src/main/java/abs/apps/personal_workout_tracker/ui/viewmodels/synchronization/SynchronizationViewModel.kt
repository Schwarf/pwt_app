package abs.apps.personal_workout_tracker.ui.viewmodels.synchronization


import abs.apps.personal_training_tracker.data.repositories.ITrainingTimestampRepository
import abs.apps.personal_workout_tracker.data.database.Training
import abs.apps.personal_workout_tracker.data.database.TrainingTimestamp
import abs.apps.personal_workout_tracker.data.database.Workout
import abs.apps.personal_workout_tracker.data.database.WorkoutTimestamp
import abs.apps.personal_workout_tracker.data.http_client.DTOConverter
import abs.apps.personal_workout_tracker.data.http_client.WorkoutDTO
import abs.apps.personal_workout_tracker.data.repositories.ISynchronizationRepository
import abs.apps.personal_workout_tracker.data.repositories.ITrainingRepository
import abs.apps.personal_workout_tracker.data.repositories.IWorkoutRepository
import abs.apps.personal_workout_tracker.data.repositories.IWorkoutTimestampRepository
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


            } catch (e: Exception) {
                Log.e("Error", "Error in ViewModel initialization", e)
            }
        }

        Log.d("Hallo", "INIT")
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


    private fun convertAndSend(
        workouts: List<Workout>, trainings: List<Training>,
        workoutTimestamps: List<WorkoutTimestamp>, trainingTimestamps: List<TrainingTimestamp>
    ) {
        val listOfWorkoutsDTO = mutableListOf<WorkoutDTO>()
        val workoutConverter = DTOConverter(Workout::class, WorkoutDTO::class)
        workouts.forEach {
            listOfWorkoutsDTO.add(workoutConverter.convert(it))
        }


        Log.d("WORKOUTLIST ", listOfWorkoutsDTO.size.toString())
        val workoutWrapper = mapOf("workouts" to listOfWorkoutsDTO)
        val urlWorkouts = "http://10.0.2.2:8000/insert_workouts/"
        val urlTrainings = "http://192.168.0.227:8000/insert_trainings/"
        val gson = Gson()
        val jsonWorkouts = gson.toJson(workoutWrapper)
        Log.d("JsonWorkout", jsonWorkouts)
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")
        Fuel.post(urlWorkouts).jsonBody(jsonWorkouts).response { _, _, result ->
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