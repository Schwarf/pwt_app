package abs.apps.personal_workout_tracker.ui.navigation

import abs.apps.personal_workout_tracker.ui.screens.workouts.AddWorkoutScreen
import abs.apps.personal_workout_tracker.ui.screens.workouts.EditWorkoutDestination
import abs.apps.personal_workout_tracker.ui.screens.workouts.EditWorkoutScreen
import abs.apps.personal_workout_tracker.ui.screens.workouts.ExistingWorkoutDestination
import abs.apps.personal_workout_tracker.ui.screens.workouts.ExistingWorkoutScreen
import abs.apps.personal_workout_tracker.ui.screens.workouts.HomeDestination
import abs.apps.personal_workout_tracker.ui.screens.workouts.WorkoutListScreen
import abs.apps.personal_workout_tracker.ui.screens.StartupDestination
import abs.apps.personal_workout_tracker.ui.screens.StartupScreen
import abs.apps.personal_workout_tracker.ui.screens.workouts.WorkoutEntryDestination
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
        startDestination = StartupDestination.route,
        modifier = modifier
    ) {

        composable(route = StartupDestination.route) {
            StartupScreen(navigateToHome = { navController.navigate(HomeDestination.route) })
        }
        composable(route = HomeDestination.route) {
            WorkoutListScreen(
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
                navigateToEditWorkout = { navController.navigate("${EditWorkoutDestination.route}/$it") },
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
