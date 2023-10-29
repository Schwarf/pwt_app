package abs.apps.personal_workout_tracker.ui.screens.trainings

import abs.apps.personal_workout_tracker.R
import abs.apps.personal_workout_tracker.ui.navigation.INavigationDestination

object TrainingDatesDestination : INavigationDestination {
    override val route = "training_dates"
    override val titleRes = R.string.training_dates
    const val trainingIdArg = "trainingId"
    val routeWithArgs = "$route/{$trainingIdArg}"
}
