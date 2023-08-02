package abs.apps.personal_workout_tracker.ui.viewmodels

import abs.apps.personal_workout_tracker.data.repositories.IWorkoutRepository
import abs.apps.personal_workout_tracker.ui.viewmodels.dataUI.WorkoutUI
import abs.apps.personal_workout_tracker.ui.viewmodels.dataUI.toWorkout
import abs.apps.personal_workout_tracker.ui.viewmodels.dataUI.validateWorkoutUI
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AddWorkoutViewModel(private val workoutRepository: IWorkoutRepository) : ViewModel() {
    var state by mutableStateOf(ValidatedWorkoutUI())
        private set

    fun updateUiState(workoutUI: WorkoutUI) {
        state = ValidatedWorkoutUI(
            workoutUI = workoutUI,
            isValid = validateInput(workoutUI)
        )
    }

    suspend fun saveWorkout() {
        if (validateInput()) {
            workoutRepository.upsertWorkout(state.workoutUI.toWorkout())
        }
    }

    private fun validateInput(workoutUI : WorkoutUI= this.state.workoutUI) :Boolean
    {
        return validateWorkoutUI(workoutUI)
    }
}

data class ValidatedWorkoutUI(
    val workoutUI: WorkoutUI = WorkoutUI(),
    val isValid: Boolean = false
)



