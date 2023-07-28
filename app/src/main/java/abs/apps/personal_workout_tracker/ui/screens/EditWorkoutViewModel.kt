package abs.apps.personal_workout_tracker.ui.screens

import abs.apps.personal_workout_tracker.data.IWorkoutRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class EditWorkoutViewModel(
    savedStateHandle: SavedStateHandle,
    private val workoutRepository: IWorkoutRepository
) : ViewModel() {

    var state by mutableStateOf(ValidatedWorkoutUI())
        private set

}