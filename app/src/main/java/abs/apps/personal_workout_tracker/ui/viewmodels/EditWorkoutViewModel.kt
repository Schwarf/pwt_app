package abs.apps.personal_workout_tracker.ui.viewmodels

import abs.apps.personal_workout_tracker.data.IWorkoutRepository
import abs.apps.personal_workout_tracker.ui.screens.EditWorkoutDestination
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditWorkoutViewModel(
    savedStateHandle: SavedStateHandle,
    private val workoutRepository: IWorkoutRepository
) : ViewModel() {

    var state by mutableStateOf(ValidatedWorkoutUI())
        private set

    private val workoutId: Int = checkNotNull(savedStateHandle[EditWorkoutDestination.workoutIdArg])

    init {
        viewModelScope.launch {
            state = workoutRepository.getWorkoutStream(workoutId)
                .filterNotNull()
                .first()
                .toValidatedWorkoutUI(true)
        }
    }

    suspend fun updateWorkout() {
        if (validateInput(state.workoutUI)) {
            workoutRepository.updateWorkout(state.workoutUI.toWorkout())
        }
    }

    fun updateUiState(workoutUI: WorkoutUI) {
        state =
            ValidatedWorkoutUI(workoutUI = workoutUI, isValid = validateInput(workoutUI))
    }

    private fun validateInput(state: WorkoutUI = this.state.workoutUI): Boolean {
        return with(state) {
            name.isNotBlank() && sets.isNotBlank() && sets.all { it.isDigit() } &&
                    totalRepetitions.isNotBlank() && totalRepetitions.all { it.isDigit() } &&
                    maxRepetitionsInSet.isNotBlank() && maxRepetitionsInSet.all { it.isDigit() }
        }
    }
}