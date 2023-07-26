package abs.apps.personal_workout_tracker.ui

import abs.apps.personal_workout_tracker.WorkoutTrackerApplication
import abs.apps.personal_workout_tracker.ui.screens_and_dialogs.HomeScreenViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for HomeViewModel
        initializer {
            HomeScreenViewModel(WorkoutTrackerApplication().container.workoutRepository)
        }
    }
}

fun CreationExtras.workoutTrackerApplication(): WorkoutTrackerApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as WorkoutTrackerApplication)
