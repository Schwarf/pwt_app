package abs.apps.personal_workout_tracker.ui.navigation

import abs.apps.personal_workout_tracker.ui.screens_and_dialogs.HomeDestination
import abs.apps.personal_workout_tracker.ui.screens_and_dialogs.HomeScreen
import abs.apps.personal_workout_tracker.ui.screens_and_dialogs.WorkoutEntryDestination
import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

/**
 * Top level composable that represents screens for the application.
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WorkoutTrackerNavigation(navController: NavHostController = rememberNavController()) {
    WorkoutTrackerNavHost(navController = navController)
}

@Composable
fun WorkoutTrackerNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToAddWorkout = { navController.navigate(WorkoutEntryDestination.route) }
            )
        }
    }
}
