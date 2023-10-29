package abs.apps.personal_workout_tracker.ui.screens.workouts

import abs.apps.personal_workout_tracker.R
import abs.apps.personal_workout_tracker.ui.AppViewModelProvider
import abs.apps.personal_workout_tracker.ui.navigation.INavigationDestination
import abs.apps.personal_workout_tracker.ui.viewmodels.workouts.WorkoutDatesViewModel
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel

object WorkoutDatesDestination : INavigationDestination {
    override val route = "workout_dates"
    override val titleRes = R.string.workout_dates
    const val workoutIdArg = "workoutId"
    val routeWithArgs = "$route/{$workoutIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutDatesScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: WorkoutDatesViewModel = viewModel(factory = AppViewModelProvider.Factory),
    workoutId: Int
) {
    LaunchedEffect(workoutId) {
        viewModel.getDatesForOneWorkout(workoutId)
        viewModel.getWorkout(workoutId)
    }
    val timestamps by viewModel.timestamps.collectAsState()
    val workout = viewModel.workout

}
