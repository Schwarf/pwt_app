package abs.apps.personal_workout_tracker.ui.viewmodels

import abs.apps.personal_workout_tracker.data.IPerformanceRepository
import abs.apps.personal_workout_tracker.data.IWorkoutRepository
import abs.apps.personal_workout_tracker.data.Performance
import abs.apps.personal_workout_tracker.data.Workout
import abs.apps.personal_workout_tracker.ui.screens.ExistingWorkoutDestination
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ExistingWorkoutViewModel(
    savedStateHandle: SavedStateHandle,
    private val workoutRepository: IWorkoutRepository,
    private val performanceRepository: IPerformanceRepository
) : ViewModel() {
    private val workoutId: Int =
        checkNotNull(savedStateHandle[ExistingWorkoutDestination.workoutIdArg])

    val existingWorkoutsState: StateFlow<ExistingWorkout> =
        workoutRepository.getWorkoutStream(workoutId).filterNotNull().flatMapLatest { workout ->
            performanceRepository.getPerformancesStreamForOneWorkout(workout.id)
                .map { performance ->
                    if (performance != null) {
                        ExistingWorkout(
                            workoutUI = workout.toWorkoutUI(),
                            performanceUI = performance.toPerformanceUI()
                        )

                    } else {
                        ExistingWorkout(
                            workoutUI = workout.toWorkoutUI(),
                            performanceUI = PerformanceUI()
                        )
                    }
                }
        }.stateIn(
            scope = viewModelScope, started = SharingStarted.WhileSubscribed(
                TIMEOUT_MILLIS
            ), initialValue = ExistingWorkout()
        )

    fun addOnePerformance() {
        viewModelScope.launch {
            if (existingWorkoutsState.value.performanceUI.isPerformanceValid) {
                val currentPerformance =
                    existingWorkoutsState.value.performanceUI.toPerformance()
                performanceRepository.updatePerformance(currentPerformance.copy(performedCounter = currentPerformance.performedCounter + 1))
            } else {
                val currentPerformance = Performance(
                    workoutId = existingWorkoutsState.value.workoutUI.id,
                    performedCounter = 1
                )
                performanceRepository.insertPerformance(currentPerformance)
            }
        }
    }

    fun removeOnePerformance() {
        viewModelScope.launch {
            if (existingWorkoutsState.value.performanceUI.isPerformanceValid) {
                val currentPerformance =
                    existingWorkoutsState.value.performanceUI.toPerformance()
                performanceRepository.updatePerformance(currentPerformance.copy(performedCounter = currentPerformance.performedCounter - 1))
            }
        }
    }


    suspend fun deleteWorkout() {
        workoutRepository.deleteWorkout(existingWorkoutsState.value.workoutUI.toWorkout())
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}

data class ExistingWorkout(
    val workoutUI: WorkoutUI = WorkoutUI(),
    val performanceUI: PerformanceUI = PerformanceUI(),
)

fun Workout.toWorkoutUI(): WorkoutUI = WorkoutUI(
    id = id,
    name = name,
    sets = sets.toString(),
    totalRepetitions = totalRepetitions.toString(),
    maxRepetitionsInSet = maxRepetitionsInSet.toString()
)

data class PerformanceUI
    (
    val id: Int = 0,
    val workoutId: Int = 0,
    val performedCounter: String = "0",
    val isPerformanceValid: Boolean = false
)

fun Performance.toPerformanceUI(): PerformanceUI = PerformanceUI(
    id = id,
    workoutId = workoutId,
    performedCounter = performedCounter.toString(),
    isPerformanceValid = true
)

fun PerformanceUI.toPerformance(): Performance = Performance(
    id = id,
    workoutId = workoutId,
    performedCounter = performedCounter.toIntOrNull() ?: 0
)