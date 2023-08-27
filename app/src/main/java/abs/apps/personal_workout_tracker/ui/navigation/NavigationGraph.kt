package abs.apps.personal_workout_tracker.ui.navigation

import abs.apps.personal_workout_tracker.data.database.Training
import abs.apps.personal_workout_tracker.ui.screens.StartDestination
import abs.apps.personal_workout_tracker.ui.screens.StartScreen
import abs.apps.personal_workout_tracker.ui.screens.workouts.AddWorkoutScreen
import abs.apps.personal_workout_tracker.ui.screens.workouts.EditWorkoutDestination
import abs.apps.personal_workout_tracker.ui.screens.workouts.EditWorkoutScreen
import abs.apps.personal_workout_tracker.ui.screens.workouts.ExistingWorkoutDestination
import abs.apps.personal_workout_tracker.ui.screens.workouts.ExistingWorkoutScreen
import abs.apps.personal_workout_tracker.ui.screens.workouts.WorkoutListDestination
import abs.apps.personal_workout_tracker.ui.screens.workouts.WorkoutListScreen
import abs.apps.personal_workout_tracker.ui.screens.StartupDestination
import abs.apps.personal_workout_tracker.ui.screens.StartupScreen
import abs.apps.personal_workout_tracker.ui.screens.trainings.AddTrainingScreen
import abs.apps.personal_workout_tracker.ui.screens.trainings.EditTrainingDestination
import abs.apps.personal_workout_tracker.ui.screens.trainings.EditTrainingScreen
import abs.apps.personal_workout_tracker.ui.screens.trainings.ExistingTrainingDestination
import abs.apps.personal_workout_tracker.ui.screens.trainings.ExistingTrainingScreen
import abs.apps.personal_workout_tracker.ui.screens.trainings.TrainingEntryDestination
import abs.apps.personal_workout_tracker.ui.screens.trainings.TrainingListDestination
import abs.apps.personal_workout_tracker.ui.screens.trainings.TrainingListScreen
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
            StartupScreen(navigateToStartScreen = { navController.navigate(StartDestination.route) })
        }

        composable(route = StartDestination.route) {
            StartScreen(navigateToWorkouts = { navController.navigate(WorkoutListDestination.route)},
                navigateToTrainings = { navController.navigate(TrainingListDestination.route)})
        }


        composable(route = TrainingListDestination.route) {
            TrainingListScreen(
                navigateToExistingTraining = {
                    navController.navigate("${ExistingTrainingDestination.route}/${it}")
                },
                navigateToAddTraining = { navController.navigate(TrainingEntryDestination.route) },
                navigateToOtherList = { navController.navigate(WorkoutListDestination.route) }
            )
        }
        composable(route = TrainingEntryDestination.route)
        {
            AddTrainingScreen(navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() })
        }
        composable(
            route = EditTrainingDestination.routeWithArgs,
            arguments = listOf(navArgument(EditTrainingDestination.trainingIdArg) {
                type = NavType.IntType
            })
        )
        {
            EditTrainingScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() })
        }
        composable(
            route = ExistingTrainingDestination.routeWithArgs,
            arguments = listOf(navArgument(ExistingTrainingDestination.trainingIdArg) {
                type = NavType.IntType
            })
        )
        {
            ExistingTrainingScreen(
                navigateToEditTraining = { navController.navigate("${EditTrainingDestination.route}/$it") },
                navigateBack = { navController.popBackStack() })
        }



        composable(route = WorkoutListDestination.route) {
            WorkoutListScreen(
                navigateToExistingWorkout = {
                    navController.navigate("${ExistingWorkoutDestination.route}/${it}")
                },
                navigateToAddWorkout = { navController.navigate(WorkoutEntryDestination.route) },
                navigateToOtherList = { navController.navigate(TrainingListDestination.route) }
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
