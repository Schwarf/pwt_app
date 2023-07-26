package abs.apps.personal_workout_tracker.ui.screens_and_dialogs

import abs.apps.personal_workout_tracker.Workout
import abs.apps.personal_workout_tracker.IWorkoutRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeScreenViewModel(workoutRepository: IWorkoutRepository) : ViewModel() {
    val homeUiState: StateFlow<HomeUiState> = workoutRepository.getAllWorkoutsStream().map{HomeUiState(it)}
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = HomeUiState()
        )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}




/**
 * Ui State for HomeScreen
 */
data class HomeUiState(val workoutList: List<Workout> = listOf())
