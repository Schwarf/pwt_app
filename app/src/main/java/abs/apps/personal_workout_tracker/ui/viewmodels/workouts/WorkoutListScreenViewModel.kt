package abs.apps.personal_workout_tracker.ui.viewmodels.workouts

import abs.apps.personal_workout_tracker.data.database.WorkoutTimestamp
import abs.apps.personal_workout_tracker.data.database.Workout
import abs.apps.personal_workout_tracker.data.repositories.IWorkoutTimestampRepository
import abs.apps.personal_workout_tracker.data.repositories.IWorkoutRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId

class WorkoutListScreenViewModel(
    private val workoutRepository: IWorkoutRepository,
    private val timestampRepository: IWorkoutTimestampRepository
) : ViewModel() {
    val listOfWorkoutsState: StateFlow<ListOfWorkouts> =
        workoutRepository.getAllWorkoutsStream().map { ListOfWorkouts(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ListOfWorkouts()
            )

    fun addPerformance(workoutId: Int) {
        viewModelScope.launch {
            val currentWorkout = workoutRepository.getWorkoutStream(workoutId).firstOrNull()

            currentWorkout?.let {
                val updatedPerformances = it.performances + 1
                workoutRepository.updateWorkoutPerformances(workoutId, updatedPerformances)
            }

            val workoutTimestamp = WorkoutTimestamp(
                workoutId = workoutId,
                timestamp = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond(),
                isDeleted = false,
                lastModified = System.currentTimeMillis()
            )
            timestampRepository.upsertTimestamp(workoutTimestamp)
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}

data class ListOfWorkouts(val workoutList: List<Workout> = listOf())
