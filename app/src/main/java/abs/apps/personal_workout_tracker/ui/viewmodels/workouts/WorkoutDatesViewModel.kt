package abs.apps.personal_workout_tracker.ui.viewmodels.workouts

import abs.apps.personal_workout_tracker.data.database.WorkoutTimestamp
import abs.apps.personal_workout_tracker.data.database.toValidatedWorkoutUI
import abs.apps.personal_workout_tracker.data.repositories.IWorkoutRepository
import abs.apps.personal_workout_tracker.data.repositories.IWorkoutTimestampRepository
import abs.apps.personal_workout_tracker.ui.viewmodels.dataUI.ValidatedWorkoutUI
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class WorkoutDatesViewModel(
    private val workoutTimestampRepository: IWorkoutTimestampRepository,
    private val workoutRepository: IWorkoutRepository
) :
    ViewModel() {
    private val _timestamps = MutableStateFlow(listOf<WorkoutTimestamp>())
    var workout by mutableStateOf(ValidatedWorkoutUI())
        private set

    val timestamps: StateFlow<List<WorkoutTimestamp>> = _timestamps

    fun getDatesForOneWorkout(workoutId: Int) {
        viewModelScope.launch {
            workoutTimestampRepository.getTimestampsStreamForOneWorkout(workoutId)
                .collect { fetchedTimestamps ->
                    _timestamps.value = fetchedTimestamps
                }
        }
    }

    fun getWorkout(workoutId: Int) {
        viewModelScope.launch {
            workout = workoutRepository.getWorkoutStream(workoutId)
                .filterNotNull()
                .first()
                .toValidatedWorkoutUI(true)
        }
    }

}
