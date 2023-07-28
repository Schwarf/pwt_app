package abs.apps.personal_workout_tracker.ui.viewmodels

import abs.apps.personal_workout_tracker.data.IWorkoutRepository
import abs.apps.personal_workout_tracker.data.Workout
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AddWorkoutViewModel(private val workoutRepository: IWorkoutRepository) : ViewModel() {
    var validatedWorkoutUIState by mutableStateOf(ValidatedWorkoutUI())
        private set

    fun updateUiState(workoutUI: WorkoutUI) {
        validatedWorkoutUIState = ValidatedWorkoutUI(
            workoutUI = workoutUI,
            isValid = validateInput(workoutUI)
        )
    }

    suspend fun saveWorkout() {
        if (validateInput()) {
            workoutRepository.insertWorkout(validatedWorkoutUIState.workoutUI.toWorkout())
        }
    }


    private fun validateInput(state: WorkoutUI = this.validatedWorkoutUIState.workoutUI): Boolean {
        return with(state) {
            name.isNotBlank() && sets.isNotBlank() && sets.all { it.isDigit() } &&
                    totalRepetitions.isNotBlank() && totalRepetitions.all { it.isDigit() } &&
                    maxRepetitionsInSet.isNotBlank() && maxRepetitionsInSet.all { it.isDigit() }
        }
    }
}

fun WorkoutUI.toWorkout(): Workout = Workout(
    id = id,
    name = name,
    sets = sets.toIntOrNull() ?: 0,
    totalRepetitions = totalRepetitions.toIntOrNull() ?: 0,
    maxRepetitionsInSet = maxRepetitionsInSet.toIntOrNull() ?: 0
)

data class ValidatedWorkoutUI(
    val workoutUI: WorkoutUI = WorkoutUI(),
    val isValid: Boolean = false
)

data class WorkoutUI(
    val id: Int = 0,
    val name: String = "",
    val sets: String = "",
    val totalRepetitions: String = "",
    val maxRepetitionsInSet: String = ""
)

fun Workout.toValidatedWorkoutUI(isValid: Boolean = false): ValidatedWorkoutUI = ValidatedWorkoutUI(
    workoutUI = this.toWorkoutUI(),
    isValid = isValid
)

