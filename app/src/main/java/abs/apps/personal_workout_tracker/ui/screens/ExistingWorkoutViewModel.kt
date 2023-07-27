package abs.apps.personal_workout_tracker.ui.screens

import abs.apps.personal_workout_tracker.data.IPerformanceRepository
import abs.apps.personal_workout_tracker.data.IWorkoutRepository
import abs.apps.personal_workout_tracker.data.Performance
import abs.apps.personal_workout_tracker.data.Workout
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ExistingWorkoutViewModel(
    savedStateHandle: SavedStateHandle,
    private val workoutRepository: IWorkoutRepository,
    private val performanceRepository: IPerformanceRepository
) : ViewModel() {
    private val workoutId: Int =
        checkNotNull(savedStateHandle[ExistingWorkoutDestination.workoutIdArg])

    val existingWorkoutsState: StateFlow<ExistingWorkout> =
        workoutRepository.getWorkoutStream(workoutId).filterNotNull().flatMapLatest {
            workout -> performanceRepository.getPerformancesStreamForOneWorkout(workout.id).map { performance ->
            ExistingWorkout(
                workoutDetails = workout.toWorkoutDetails(),
                performanceDetails = performance.toPerformanceDetails()
            )
        }}.stateIn(
            scope = viewModelScope, started = SharingStarted.WhileSubscribed(
                TIMEOUT_MILLIS
            ), initialValue = ExistingWorkout()
        )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}

data class ExistingWorkout(
    val workoutDetails: WorkoutDetails = WorkoutDetails(),
    val performanceDetails: PerformanceDetails = PerformanceDetails()
)

fun Workout.toWorkoutDetails(): WorkoutDetails = WorkoutDetails(
    id = id,
    name = name,
    sets = sets.toString(),
    totalRepetitions = totalRepetitions.toString(),
    maxRepetitionsInSet = maxRepetitionsInSet.toString()
)

data class PerformanceDetails
    (
    val id: Int = 0,
    val workoutId: Int = 0,
    val performedCounter: String = "",
    val timestamp: Long = 0

)

fun Performance.toPerformanceDetails(): PerformanceDetails = PerformanceDetails(
    id = id,
    workoutId = workoutId,
    performedCounter = performedCounter.toString(),
)
