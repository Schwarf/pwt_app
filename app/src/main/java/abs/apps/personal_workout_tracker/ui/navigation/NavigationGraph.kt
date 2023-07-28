package abs.apps.personal_workout_tracker.ui.navigation

import abs.apps.personal_workout_tracker.ui.screens.AddWorkoutScreen
import abs.apps.personal_workout_tracker.ui.screens.EditWorkoutDestination
import abs.apps.personal_workout_tracker.ui.screens.EditWorkoutScreen
import abs.apps.personal_workout_tracker.ui.screens.ExistingWorkoutDestination
import abs.apps.personal_workout_tracker.ui.screens.ExistingWorkoutScreen
import abs.apps.personal_workout_tracker.ui.screens.HomeDestination
import abs.apps.personal_workout_tracker.ui.screens.HomeScreen
import abs.apps.personal_workout_tracker.ui.screens.WorkoutEntryDestination
import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

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
                navigateToExistingWorkout = {
                    navController.navigate("${ExistingWorkoutDestination.route}/${it}")
                },
                navigateToAddWorkout = { navController.navigate(WorkoutEntryDestination.route) }
            )
        }

        composable(route = WorkoutEntryDestination.route)
        {
            AddWorkoutScreen(navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() })
        }

        composable(
            route = ExistingWorkoutDestination.routeWithArgs,
            arguments = listOf(navArgument(ExistingWorkoutDestination.workoutIdArg) {
                type = NavType.IntType
            })
        )
        {
            ExistingWorkoutScreen(
                navigateToExistingWorkout = { navController.navigate("${ExistingWorkoutDestination.route}/$it") },
                navigateBack = { navController.popBackStack() })
        }

        composable(
            route = EditWorkoutDestination.routeWithArgs,
            arguments = listOf(navArgument(EditWorkoutDestination.workoutIdArg) {
                type = NavType.IntType
            })
        )
        {
            EditWorkoutScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() })
        }

    }
}
