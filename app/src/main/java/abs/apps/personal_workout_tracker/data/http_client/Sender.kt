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
import abs.apps.personal_workout_tracker.ui.viewmodels.trainings.ListOfTrainings
import abs.apps.personal_workout_tracker.ui.viewmodels.workouts.ListOfWorkouts
import kotlinx.coroutines.flow.toList

class Sender (private val synchronizationRepository: ISynchronizationRepository,
              private val workoutRepository: IWorkoutRepository,
              private val trainingRepository: ITrainingRepository,
              private val workoutTimestampRepository: IWorkoutTimestampRepository,
              private val trainingTimestampRepository: ITrainingTimestampRepository,

    ){
    private val synchronizationNeeded = false
    private var listOfWorkouts: List<Workout> = emptyList()
    private var listOfTrainings: List<Training> = emptyList()
    private var listOfWorkoutTimestamps: List<WorkoutTimestamp> = emptyList()
    private var listOfTrainingTimestamp: List<TrainingTimestamp> = emptyList()
    suspend fun checkIsSynchronizationFeasible() : Boolean
    {
        val timestamp: Long = synchronizationRepository.getLatestSuccessfulSynchronization()
        val test = workoutRepository.getUpdatesForSynchronization(timestamp)
        listOfWorkouts = test
    }
    
    fun sendWorkout(workout: Workout)
    {
        val workoutDTO = WorkoutDTO(
            name = workout.name,
            sets = workout.sets,
            totalRepetitions = workout.totalRepetitions,
            maxRepetitionsInSet = workout.maxRepetitionsInSet,
            performances = workout.performances,
            id = workout.id,
            isDeleted = workout.isDeleted
        )

    }
}