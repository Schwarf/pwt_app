package abs.apps.personal_workout_tracker.ui.screens.workouts

import abs.apps.personal_workout_tracker.R
import abs.apps.personal_workout_tracker.data.database.Workout
import abs.apps.personal_workout_tracker.data.database.WorkoutTimestamp
import abs.apps.personal_workout_tracker.ui.AppViewModelProvider
import abs.apps.personal_workout_tracker.ui.navigation.INavigationDestination
import abs.apps.personal_workout_tracker.ui.screens.helpers.AppTopBar
import abs.apps.personal_workout_tracker.ui.viewmodels.dataUI.ValidatedWorkoutUI
import abs.apps.personal_workout_tracker.ui.viewmodels.dataUI.toWorkout
import abs.apps.personal_workout_tracker.ui.viewmodels.workouts.WorkoutDatesViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

object WorkoutDatesDestination : INavigationDestination {
    override val route = "workout_dates"
    override val titleRes = R.string.workout_dates
    const val workoutIdArg = "workoutId"
    val routeWithArgs = "$route/{$workoutIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutDatesScreen(
    onNavigateUp: () -> Unit,
    viewModel: WorkoutDatesViewModel = viewModel(factory = AppViewModelProvider.Factory),
    workoutId: Int
) {
    LaunchedEffect(workoutId) {
        viewModel.getDatesForOneWorkout(workoutId)
        viewModel.getWorkout(workoutId)
    }
    val timestamps by viewModel.timestamps.collectAsState()
    val workout = viewModel.workout.toWorkout()
    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(WorkoutDatesDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },

    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            WorkoutItem(
                workout = workout,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            timestamps.forEach { timestamp ->
                val dateTime = LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(timestamp.timestamp),
                    ZoneId.systemDefault()
                )
                Text(
                    text = dateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }

}


