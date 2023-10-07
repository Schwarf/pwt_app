package abs.apps.personal_workout_tracker.ui.viewmodels.synchronization


import abs.apps.personal_training_tracker.data.repositories.ITrainingTimestampRepository
import abs.apps.personal_workout_tracker.data.database.Training
import abs.apps.personal_workout_tracker.data.database.Workout
import abs.apps.personal_workout_tracker.data.http_client.DTOConverter
import abs.apps.personal_workout_tracker.data.http_client.Sender
import abs.apps.personal_workout_tracker.data.http_client.WorkoutDTO
import abs.apps.personal_workout_tracker.data.repositories.ISynchronizationRepository
import abs.apps.personal_workout_tracker.data.repositories.ITrainingRepository
import abs.apps.personal_workout_tracker.data.repositories.IWorkoutRepository
import abs.apps.personal_workout_tracker.data.repositories.IWorkoutTimestampRepository
import abs.apps.personal_workout_tracker.ui.viewmodels.trainings.ListOfTrainings
import abs.apps.personal_workout_tracker.ui.viewmodels.trainings.TrainingListScreenViewModel
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SynchronizationViewModel(private val synchronizationRepository: ISynchronizationRepository,
                               private val workoutRepository: IWorkoutRepository,
                               private val trainingRepository: ITrainingRepository,
                               private val workoutTimestampRepository: IWorkoutTimestampRepository,
                               private val trainingTimestampRepository: ITrainingTimestampRepository) : ViewModel() {
    private val syncTimestamp = MutableStateFlow(0L)
    private val workoutsSync: MutableStateFlow<List<Workout>> = MutableStateFlow(emptyList())
    private val trainingsSync: MutableStateFlow<List<Training>> = MutableStateFlow(emptyList())
    init {
        viewModelScope.launch {
            try {
                val timestamp = synchronizationRepository.getLatestSuccessfulSynchronization()
                syncTimestamp.value = timestamp
                val workouts = workoutRepository.getUpdatesForSynchronization(syncTimestamp.value)
                workoutsSync.value = workouts
                val trainings = trainingRepository.getUpdatesForSynchronization(syncTimestamp.value)
                trainingsSync.value = trainings
            } catch (e: Exception) {
                // handle the error appropriately
            }
        }

    }

    fun convertAndSend()
    {
        val listOfWorkoutsDTO = mutableListOf<WorkoutDTO>()
        val workoutConverter = DTOConverter(Workout::class, WorkoutDTO::class)
        workoutsSync.value.forEach {
            listOfWorkoutsDTO.add(workoutConverter.convert(it))
        }
        val workoutWrapper = mapOf("workouts" to listOfWorkoutsDTO)
        val urlWorkouts = "http://10.0.2.2:8000/insert_workouts/"
        val urlTrainings = "http://192.168.0.227:8000/insert_trainings/"
        val gson = Gson()
        val jsonWorkouts = gson.toJson(workoutWrapper)
        Log.d("JsonWorkout", jsonWorkouts)
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