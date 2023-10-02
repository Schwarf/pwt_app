package abs.apps.personal_workout_tracker.ui.viewmodels.synchronization

import abs.apps.personal_training_tracker.data.repositories.ITrainingTimestampRepository
import abs.apps.personal_workout_tracker.data.database.Training
import abs.apps.personal_workout_tracker.data.database.TrainingTimestamp
import abs.apps.personal_workout_tracker.data.database.Workout
import abs.apps.personal_workout_tracker.data.database.WorkoutTimestamp
import abs.apps.personal_workout_tracker.data.repositories.ISynchronizationRepository
import abs.apps.personal_workout_tracker.data.repositories.ITrainingRepository
import abs.apps.personal_workout_tracker.data.repositories.IWorkoutRepository
import abs.apps.personal_workout_tracker.data.repositories.IWorkoutTimestampRepository
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toList

class SynchronizationViewModel(
    private val synchronizationRepository: ISynchronizationRepository,
    private val workoutRepository: IWorkoutRepository,
    private val trainingRepository: ITrainingRepository,
    private val workoutTimestampRepository: IWorkoutTimestampRepository,
    private val trainingTimestampRepository: ITrainingTimestampRepository
) : ViewModel() {
    private var _listOfWorkouts: List<Workout> = emptyList()
    private var _listOfTrainings: List<Training> = emptyList()
    private var _listOfWorkoutTimestamps: List<WorkoutTimestamp> = emptyList()
    private var _listOfTrainingTimestamps: List<TrainingTimestamp> = emptyList()

    @OptIn(FlowPreview::class)
    suspend fun checkIsSynchronizationFeasible(): Boolean {
        val lastSynchronizationTimestamp: Long =
            synchronizationRepository.getLatestSuccessfulSynchronization()

        val flowOfWorkoutsToSynchronize: Flow<List<Workout>> =
            workoutRepository.getUpdatesForSynchronization(lastSynchronizationTimestamp)
        _listOfWorkouts = flowOfWorkoutsToSynchronize.flatMapConcat { it.asFlow() }.toList()

        val flowOfTrainingsToSynchronize: Flow<List<Training>> =
            trainingRepository.getUpdatesForSynchronization(lastSynchronizationTimestamp)
        _listOfTrainings = flowOfTrainingsToSynchronize.flatMapConcat { it.asFlow() }.toList()

        val flowOfWorkoutTimestampsToSynchronize: Flow<List<WorkoutTimestamp>> =
            workoutTimestampRepository.getUpdatesForSynchronization(lastSynchronizationTimestamp)
        _listOfWorkoutTimestamps =
            flowOfWorkoutTimestampsToSynchronize.flatMapConcat { it.asFlow() }.toList()

        val flowOfTrainingTimestampsToSynchronize: Flow<List<TrainingTimestamp>> =
            trainingTimestampRepository.getUpdatesForSynchronization(lastSynchronizationTimestamp)
        _listOfTrainingTimestamps =
            flowOfTrainingTimestampsToSynchronize.flatMapConcat { it.asFlow() }.toList()


        return _listOfWorkouts.isNotEmpty() || _listOfTrainings.isNotEmpty() ||
                _listOfTrainingTimestamps.isNotEmpty() || _listOfWorkoutTimestamps.isNotEmpty()
    }




    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }


}