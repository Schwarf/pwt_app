package abs.apps.personal_workout_tracker.ui.screens

import abs.apps.personal_workout_tracker.R
import abs.apps.personal_workout_tracker.ui.navigation.INavigationDestination

object EditWorkoutDestination : INavigationDestination {
    override val route = "workout_edit"
    override val titleRes = R.string.edit_workout_title
    const val itemIdArg = "workoutId"
    val routeWithArgs = "$route/{$itemIdArg}"
}

