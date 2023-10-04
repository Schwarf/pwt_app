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
    private val listOfTrainingTimestamp = mutableListOf<TrainingTimestamp>()

    private val listOfWorkoutsDTO = mutableListOf<WorkoutDTO>()
    private val listOfTrainingsDTO = mutableListOf<TrainingDTO>()
    private val listOfWorkoutTimestampsDTO = mutableListOf<WorkoutTimestampDTO>()
    private val listOfTrainingTimestampDTO = mutableListOf<TrainingTimestampDTO>()

    suspend fun checkIsSynchronizationFeasible(): Boolean {
        val timestamp: Long = synchronizationRepository.getLatestSuccessfulSynchronization()

        workoutRepository.getUpdatesForSynchronization(timestamp)
            .collect { workouts -> listOfWorkouts.addAll(workouts) }
        trainingRepository.getUpdatesForSynchronization(timestamp)
            .collect { trainings -> listOfTrainings.addAll(trainings) }
        workoutTimestampRepository.getUpdatesForSynchronization(timestamp)
            .collect { workoutTimestamps -> listOfWorkoutTimestamps.addAll(workoutTimestamps) }
        trainingTimestampRepository.getUpdatesForSynchronization(timestamp)
            .collect { trainingTimestamps -> listOfTrainingTimestamp.addAll(trainingTimestamps) }
        return listOfWorkouts.isNotEmpty() || listOfTrainings.isNotEmpty() ||
                listOfTrainingTimestamp.isNotEmpty() ||
                listOfWorkoutTimestamps.isNotEmpty()
    }

    fun convert() {
        val workoutConverter = DTOConverter(Workout::class, WorkoutDTO::class)
        listOfWorkouts.forEach{
            listOfWorkoutsDTO.add(workoutConverter.convert(it))
        }
        val trainingConverter = DTOConverter(Training::class, TrainingDTO::class)
        listOfTrainings.forEach{
            listOfTrainingsDTO.add(trainingConverter.convert(it))
        }

    }
}