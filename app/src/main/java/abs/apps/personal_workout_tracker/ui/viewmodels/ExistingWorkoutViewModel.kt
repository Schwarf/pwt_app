package abs.apps.personal_workout_tracker.ui.viewmodels

import abs.apps.personal_workout_tracker.data.database.Timestamp
import abs.apps.personal_workout_tracker.data.database.toTimestampUI
import abs.apps.personal_workout_tracker.data.database.toWorkoutUI
import abs.apps.personal_workout_tracker.data.repositories.ITimestampRepository
import abs.apps.personal_workout_tracker.data.repositories.IWorkoutRepository
import abs.apps.personal_workout_tracker.ui.screens.ExistingWorkoutDestination
import abs.apps.personal_workout_tracker.ui.viewmodels.dataUI.TimestampUI
import abs.apps.personal_workout_tracker.ui.viewmodels.dataUI.WorkoutUI
import abs.apps.personal_workout_tracker.ui.viewmodels.dataUI.toTimestamp
import abs.apps.personal_workout_tracker.ui.viewmodels.dataUI.toWorkout
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId

class ExistingWorkoutViewModel(
    savedStateHandle: SavedStateHandle,
    private val workoutRepository: IWorkoutRepository,
    private val timestampRepository: ITimestampRepository
) : ViewModel() {
    private val workoutId: Int =
        checkNotNull(savedStateHandle[ExistingWorkoutDestination.workoutIdArg])

    @OptIn(ExperimentalCoroutinesApi::class)
    val existingWorkoutsState: StateFlow<ExistingWorkout> =
        workoutRepository.getWorkoutStream(workoutId).filterNotNull().flatMapLatest { workout ->
            timestampRepository.getLatestTimestampStreamForOneWorkout(workout.id)
                .map { timestamp ->
                    if (timestamp != null)
                        ExistingWorkout(
                            workoutUI = workout.toWorkoutUI(),
                            timestampUI = timestamp.toTimestampUI()
                        )
                    else
                        ExistingWorkout(
                            workoutUI = workout.toWorkoutUI().copy(performances = "0"),
                            timestampUI = TimestampUI()
                        )

                }
        }.stateIn(
            scope = viewModelScope, started = SharingStarted.WhileSubscribed(
                TIMEOUT_MILLIS
            ), initialValue = ExistingWorkout()
        )


    fun addOnePerformance() {
        viewModelScope.launch {
            val currentWorkout = existingWorkoutsState.value.workoutUI.toWorkout()
            workoutRepository.upsertWorkout(currentWorkout.copy(performances = currentWorkout.performances + 1))
            val currentTimestamp = Timestamp(
                workoutId = existingWorkoutsState.value.workoutUI.id,
                timestamp = LocalDateTime.now().atZone(
                    ZoneId.systemDefault()
                ).toEpochSecond()
            )
            timestampRepository.upsertTimestamp(currentTimestamp)
        }
    }

    fun removeOnePerformance() {
        viewModelScope.launch {
            if (existingWorkoutsState.value.workoutUI.toWorkout().performances > 0) {
                val currentWorkout = existingWorkoutsState.value.workoutUI.toWorkout()
                workoutRepository.upsertWorkout(currentWorkout.copy(performances = currentWorkout.performances - 1))
                timestampRepository.deleteTimestamp(existingWorkoutsState.value.timestampUI.toTimestamp())
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
    val timestampUI: TimestampUI = TimestampUI()
)

