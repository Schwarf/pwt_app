package abs.apps.personal_workout_tracker.ui.screens.trainings

import abs.apps.personal_workout_tracker.R
import abs.apps.personal_workout_tracker.ui.navigation.INavigationDestination

object ExistingTrainingDestination : INavigationDestination {
    override val route = "existing_training"
    override val titleRes = R.string.workout_exists
    const val trainingIdArg = "trainingId"
    val routeWithArgs = "$route/{$trainingIdArg}"
}
