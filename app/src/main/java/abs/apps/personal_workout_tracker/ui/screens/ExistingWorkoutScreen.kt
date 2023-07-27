package abs.apps.personal_workout_tracker.ui.screens

import abs.apps.personal_workout_tracker.R
import abs.apps.personal_workout_tracker.ui.navigation.INavigationDestination

object ExistingWorkoutDestination : INavigationDestination {
    override val route = "existing_workout"
    override val titleRes = R.string.workout_exists
    const val workoutIdArg = "workoutId"
    val routeWithArgs = "$route/{$workoutIdArg}"
}
