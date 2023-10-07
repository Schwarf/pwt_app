package abs.apps.personal_workout_tracker.data.http_client

import abs.apps.personal_training_tracker.data.repositories.ITrainingTimestampRepository
import abs.apps.personal_workout_tracker.data.database.Training
import abs.apps.personal_workout_tracker.data.database.TrainingTimestamp
import abs.apps.personal_workout_tracker.data.database.Workout
import abs.apps.personal_workout_tracker.data.database.WorkoutTimestamp
import abs.apps.personal_workout_tracker.data.repositories.ISynchronizationRepository
import abs.apps.personal_workout_tracker.data.repositories.ITrainingRepository
import abs.apps.personal_workout_tracker.data.repositories.IWorkoutRepository
import abs.apps.personal_workout_tracker.data.repositories.IWorkoutTimestampRepository
import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.result.Result
import com.google.gson.Gson

class Sender(
    private val synchronizationRepository: ISynchronizationRepository,
    private val workoutRepository: IWorkoutRepository,
    private val trainingRepository: ITrainingRepository,
    private val workoutTimestampRepository: IWorkoutTimestampRepository,
    private val trainingTimestampRepository: ITrainingTimestampRepository,

    ) {
    private val synchronizationNeeded = false
    private val listOfWorkouts = mutableListOf<Workout>()
    private val listOfTrainings = mutableListOf<Training>()
    private val listOfWorkoutTimestamps = mutableListOf<WorkoutTimestamp>()
    private val listOfTrainingTimestamps = mutableListOf<TrainingTimestamp>()


    suspend fun isSynchronizationFeasible(): Boolean {
        val timestamp: Long = synchronizationRepository.getLatestSuccessfulSynchronization()

        workoutRepository.getUpdatesForSynchronization(timestamp)
            .collect { workouts -> listOfWorkouts.addAll(workouts) }
        trainingRepository.getUpdatesForSynchronization(timestamp)
            .collect { trainings -> listOfTrainings.addAll(trainings) }
        workoutTimestampRepository.getUpdatesForSynchronization(timestamp)
            .collect { workoutTimestamps -> listOfWorkoutTimestamps.addAll(workoutTimestamps) }
        trainingTimestampRepository.getUpdatesForSynchronization(timestamp)
            .collect { trainingTimestamps -> listOfTrainingTimestamps.addAll(trainingTimestamps) }
        return listOfWorkouts.isNotEmpty() || listOfTrainings.isNotEmpty() ||
                listOfTrainingTimestamps.isNotEmpty() ||
                listOfWorkoutTimestamps.isNotEmpty()
    }

    fun convertAndSend() {
        val listOfWorkoutsDTO = mutableListOf<WorkoutDTO>()
        val listOfTrainingsDTO = mutableListOf<TrainingDTO>()
        val listOfWorkoutTimestampsDTO = mutableListOf<WorkoutTimestampDTO>()
        val listOfTrainingTimestampsDTO = mutableListOf<TrainingTimestampDTO>()

        val workoutConverter = DTOConverter(Workout::class, WorkoutDTO::class)
        listOfWorkouts.forEach {
            listOfWorkoutsDTO.add(workoutConverter.convert(it))
        }
        val trainingConverter = DTOConverter(Training::class, TrainingDTO::class)
        listOfTrainings.forEach {
            listOfTrainingsDTO.add(trainingConverter.convert(it))
        }
        val workoutTimestampConverter =
            DTOConverter(WorkoutTimestamp::class, WorkoutTimestampDTO::class)
        listOfWorkoutTimestamps.forEach {
            listOfWorkoutTimestampsDTO.add(workoutTimestampConverter.convert(it))
        }
        val trainingTimestampConverter =
            DTOConverter(TrainingTimestamp::class, TrainingTimestampDTO::class)
        listOfTrainingTimestamps.forEach {
            listOfTrainingTimestampsDTO.add(trainingTimestampConverter.convert(it))
        }

        val workoutWrapper = mapOf("workouts" to listOfWorkoutsDTO)
        val trainingWrapper = mapOf("trainings" to listOfTrainingsDTO)
        val workoutTimestampWrapper = mapOf("workout_timestamps" to listOfWorkoutTimestampsDTO)
        val trainingTimestampWrapper = mapOf("training_timestamps" to listOfTrainingTimestampsDTO)
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json")

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

