package abs.apps.personal_workout_tracker.ui.screens_and_dialogs

import abs.apps.personal_workout_tracker.data.IWorkoutRepository
import abs.apps.personal_workout_tracker.data.Workout
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AddWorkoutViewModel(private val workoutRepository: IWorkoutRepository) : ViewModel() {
    var state by mutableStateOf(WorkoutEntry())
        private set

    fun updateUiState(workoutDetails: WorkoutDetails) {
        state = WorkoutEntry(
            workoutDetails = workoutDetails,
            isEntryValid = validateInput(workoutDetails)
        )
    }

    suspend fun saveWorkout() {
        if (validateInput()) {
            workoutRepository.insertWorkout(state.workoutDetails.toWorkout())
        }
    }


    private fun validateInput(state: WorkoutDetails = this.state.workoutDetails): Boolean {
        return with(state) {
            name.isNotBlank() && sets.isNotBlank() && sets.all { it.isDigit() } &&
                    totalRepetitions.isNotBlank() && totalRepetitions.all { it.isDigit() } &&
                    maxRepetitionsInSet.isNotBlank() && maxRepetitionsInSet.all { it.isDigit() }
        }

    }
}

fun WorkoutDetails.toWorkout(): Workout = Workout(
    id = id,
    name = name,
    sets = sets.toIntOrNull() ?: 0,
    totalRepetitions = totalRepetitions.toIntOrNull() ?: 0,
    maxRepetitionsInSet = maxRepetitionsInSet.toIntOrNull() ?: 0
)

data class WorkoutEntry(
    val workoutDetails: WorkoutDetails = WorkoutDetails(),
    val isEntryValid: Boolean = false
)

data class WorkoutDetails(
    val id: Int = 0,
    val name: String = "",
    val sets: String = "",
    val totalRepetitions: String = "",
    val maxRepetitionsInSet: String = ""
)
