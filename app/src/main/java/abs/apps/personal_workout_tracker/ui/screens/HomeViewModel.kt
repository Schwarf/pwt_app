package abs.apps.personal_workout_tracker.ui.screens

import abs.apps.personal_workout_tracker.data.IWorkoutRepository
import abs.apps.personal_workout_tracker.data.Workout
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(workoutRepository: IWorkoutRepository) : ViewModel() {
    val listOfWorkoutsState: StateFlow<ListOfWorkouts> =
        workoutRepository.getAllWorkoutsStream().map { ListOfWorkouts(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ListOfWorkouts()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * Ui State for HomeScreen
 */
data class ListOfWorkouts(val workoutList: List<Workout> = listOf())
