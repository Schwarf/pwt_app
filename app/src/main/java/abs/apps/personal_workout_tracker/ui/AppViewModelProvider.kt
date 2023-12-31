package abs.apps.personal_workout_tracker.ui

import abs.apps.personal_training_tracker.ui.viewmodels.trainings.ExistingTrainingViewModel
import abs.apps.personal_workout_tracker.WorkoutTrackerApplication
import abs.apps.personal_workout_tracker.data.http_client.Sender
import abs.apps.personal_workout_tracker.ui.screens.workouts.WorkoutDatesScreen
import abs.apps.personal_workout_tracker.ui.viewmodels.synchronization.SynchronizationViewModel
import abs.apps.personal_workout_tracker.ui.viewmodels.trainings.AddTrainingViewModel
import abs.apps.personal_workout_tracker.ui.viewmodels.trainings.EditTrainingViewModel
import abs.apps.personal_workout_tracker.ui.viewmodels.trainings.TrainingDatesViewModel
import abs.apps.personal_workout_tracker.ui.viewmodels.trainings.TrainingListScreenViewModel
import abs.apps.personal_workout_tracker.ui.viewmodels.workouts.AddWorkoutViewModel
import abs.apps.personal_workout_tracker.ui.viewmodels.workouts.EditWorkoutViewModel
import abs.apps.personal_workout_tracker.ui.viewmodels.workouts.ExistingWorkoutViewModel
import abs.apps.personal_workout_tracker.ui.viewmodels.workouts.WorkoutDatesViewModel
import abs.apps.personal_workout_tracker.ui.viewmodels.workouts.WorkoutListScreenViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for HomeViewModel
        initializer {
            SynchronizationViewModel(
                    workoutTrackerApplication().container.synchronizationRepository,
                    workoutTrackerApplication().container.workoutRepository,
                    workoutTrackerApplication().container.trainingRepository,
                    workoutTrackerApplication().container.workoutTimestampRepository,
                    workoutTrackerApplication().container.trainingTimestampRepository
            )
        }
        initializer {
            WorkoutListScreenViewModel(
                workoutTrackerApplication().container.workoutRepository,
                workoutTrackerApplication().container.workoutTimestampRepository
            )
        }
        initializer {
            TrainingListScreenViewModel(
                workoutTrackerApplication().container.trainingRepository,
                workoutTrackerApplication().container.trainingTimestampRepository
            )
        }

        initializer {
            AddWorkoutViewModel(workoutTrackerApplication().container.workoutRepository)
        }
        initializer {
            AddTrainingViewModel(workoutTrackerApplication().container.trainingRepository)
        }
        initializer {
            ExistingWorkoutViewModel(
                this.createSavedStateHandle(),
                workoutTrackerApplication().container.workoutRepository,
                workoutTrackerApplication().container.workoutTimestampRepository
            )
        }

        initializer {
            ExistingTrainingViewModel(
                this.createSavedStateHandle(),
                workoutTrackerApplication().container.trainingRepository,
                workoutTrackerApplication().container.trainingTimestampRepository
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

        initializer {
            WorkoutDatesViewModel(
                workoutTrackerApplication().container.workoutTimestampRepository,
                workoutTrackerApplication().container.workoutRepository
            )
        }

        initializer {
            TrainingDatesViewModel(
                workoutTrackerApplication().container.trainingTimestampRepository,
                workoutTrackerApplication().container.trainingRepository
            )
        }

    }
}

fun CreationExtras.workoutTrackerApplication(): WorkoutTrackerApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as WorkoutTrackerApplication)
