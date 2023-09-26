package abs.apps.personal_workout_tracker.ui.viewmodels.workouts

import abs.apps.personal_workout_tracker.data.database.Workout
import abs.apps.personal_workout_tracker.data.database.WorkoutTimestamp
import abs.apps.personal_workout_tracker.data.repositories.IWorkoutTimestampRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ListWorkoutDatesViewModel (private val workoutTimestampRepository: IWorkoutTimestampRepository) : ViewModel() {
    private val _timestamps = MutableStateFlow(listOf<WorkoutTimestamp>())
    val timestamps: StateFlow<List<WorkoutTimestamp> > = _timestamps

    fun getDatesForOneWorkout(workoutId: Int) {
        viewModelScope.launch{
            workoutTimestampRepository.getTimestampsStreamForOneWorkout(workoutId).collect{
                    fetchedTimestamps ->
                _timestamps.value = fetchedTimestamps
            }
        }
    }
}
