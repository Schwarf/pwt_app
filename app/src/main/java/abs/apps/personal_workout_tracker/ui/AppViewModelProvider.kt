package abs.apps.personal_workout_tracker.ui

import abs.apps.personal_workout_tracker.WorkoutTrackerApplication
import abs.apps.personal_workout_tracker.ui.viewmodels.AddWorkoutViewModel
import abs.apps.personal_workout_tracker.ui.viewmodels.EditTrainingViewModel
import abs.apps.personal_workout_tracker.ui.viewmodels.EditWorkoutViewModel
import abs.apps.personal_workout_tracker.ui.viewmodels.ExistingWorkoutViewModel
import abs.apps.personal_workout_tracker.ui.viewmodels.TrainingListViewModel
import abs.apps.personal_workout_tracker.ui.viewmodels.WorkoutListScreenViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for HomeViewModel
        initializer {
            WorkoutListScreenViewModel(workoutTrackerApplication().container.workoutRepository,
                workoutTrackerApplication().container.timestampRepository)
        }
        initializer {
            TrainingListViewModel(workoutTrackerApplication().container.trainingRepository,
                workoutTrackerApplication().container.timestampRepository)
        }

        initializer {
            AddWorkoutViewModel(workoutTrackerApplication().container.workoutRepository)
        }
        initializer {
            ExistingWorkoutViewModel(
                this.createSavedStateHandle(),
                workoutTrackerApplication().container.workoutRepository,
                workoutTrackerApplication().container.timestampRepository
            )
        }
        initializer {
            EditWorkoutViewModel(
                this.createSavedStateHandle(),
                workoutTrackerApplication().container.workoutRepository,
            )
        }
        initializer {
            EditTrainingViewModel(
                this.createSavedStateHandle(),
                workoutTrackerApplication().container.trainingRepository,
            )
        }

    }
}

fun CreationExtras.workoutTrackerApplication(): WorkoutTrackerApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as WorkoutTrackerApplication)
