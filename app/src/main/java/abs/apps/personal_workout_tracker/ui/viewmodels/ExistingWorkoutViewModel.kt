package abs.apps.personal_workout_tracker.ui.viewmodels

import abs.apps.personal_workout_tracker.data.database.Performance
import abs.apps.personal_workout_tracker.data.database.Timestamp
import abs.apps.personal_workout_tracker.data.database.Workout
import abs.apps.personal_workout_tracker.data.database.toPerformanceUI
import abs.apps.personal_workout_tracker.data.database.toTimestampUI
import abs.apps.personal_workout_tracker.data.database.toWorkoutUI
import abs.apps.personal_workout_tracker.data.repositories.IPerformanceRepository
import abs.apps.personal_workout_tracker.data.repositories.ITimestampRepository
import abs.apps.personal_workout_tracker.data.repositories.IWorkoutRepository
import abs.apps.personal_workout_tracker.ui.screens.ExistingWorkoutDestination
import abs.apps.personal_workout_tracker.ui.viewmodels.dataUI.PerformanceUI
import abs.apps.personal_workout_tracker.ui.viewmodels.dataUI.TimestampUI
import abs.apps.personal_workout_tracker.ui.viewmodels.dataUI.WorkoutUI
import abs.apps.personal_workout_tracker.ui.viewmodels.dataUI.toPerformance
import abs.apps.personal_workout_tracker.ui.viewmodels.dataUI.toWorkout
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ExistingWorkoutViewModel(
    savedStateHandle: SavedStateHandle,
    private val workoutRepository: IWorkoutRepository,
    private val performanceRepository: IPerformanceRepository,
    private val timestampRepository: ITimestampRepository
) : ViewModel() {
    private val workoutId: Int =
        checkNotNull(savedStateHandle[ExistingWorkoutDestination.workoutIdArg])

    val existingWorkoutsState: StateFlow<ExistingWorkout> =
        workoutRepository.getWorkoutStream(workoutId)
            .filterNotNull()
            .flatMapLatest { workout ->
                mapWorkoutToExistingWorkout(workout)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ExistingWorkout()
            )

    private fun mapWorkoutToExistingWorkout(workout: Workout): Flow<ExistingWorkout> {
        return performanceRepository.getPerformancesStreamForOneWorkout(workout.id)
            .flatMapLatest { performance ->
                mapPerformanceToExistingWorkout(workout, performance)
            }
    }

    private fun mapPerformanceToExistingWorkout(workout: Workout, performance: Performance?): Flow<ExistingWorkout> {
        return timestampRepository.getLatestTimestampStreamForOneWorkout(workout.id)
            .map { timestamp ->
                mapTimestampToExistingWorkout(workout, performance, timestamp)
            }
    }

    private fun mapTimestampToExistingWorkout(
        workout: Workout,
        performance: Performance?,
        timestamp: Timestamp?
    ): ExistingWorkout {
        return if (performance != null && timestamp != null) {
            ExistingWorkout(
                workoutUI = workout.toWorkoutUI(),
                performanceUI = performance.toPerformanceUI(),
                timestampUI = timestamp.toTimestampUI()
            )
        } else if (performance != null) {
            ExistingWorkout(
                workoutUI = workout.toWorkoutUI(),
                performanceUI = performance.toPerformanceUI(),
                timestampUI = TimestampUI()
            )
        } else if (timestamp != null) {
            ExistingWorkout(
                workoutUI = workout.toWorkoutUI(),
                performanceUI = PerformanceUI(),
                timestampUI = timestamp.toTimestampUI()
            )
        } else {
            ExistingWorkout(
                workoutUI = workout.toWorkoutUI(),
                performanceUI = PerformanceUI(),
                timestampUI = TimestampUI()
            )
        }
    }

    fun addOnePerformance() {
        viewModelScope.launch {
            if (existingWorkoutsState.value.performanceUI.isPerformanceValid) {
                val currentPerformance = existingWorkoutsState.value.performanceUI.toPerformance()
                performanceRepository.upsertPerformance(currentPerformance.copy(performedCounter = currentPerformance.performedCounter + 1))
            } else {
                val currentPerformance = Performance(
                    workoutId = existingWorkoutsState.value.workoutUI.id, performedCounter = 1
                )
                performanceRepository.upsertPerformance(currentPerformance)
            }
        }
    }

    fun removeOnePerformance() {
        viewModelScope.launch {
            if (existingWorkoutsState.value.performanceUI.isPerformanceValid) {
                val currentPerformance = existingWorkoutsState.value.performanceUI.toPerformance()
                performanceRepository.upsertPerformance(currentPerformance.copy(performedCounter = currentPerformance.performedCounter - 1))
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
    val timestampUI: TimestampUI = TimestampUI()
)

