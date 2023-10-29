package abs.apps.personal_workout_tracker.ui.screens.trainings

import abs.apps.personal_workout_tracker.R
import abs.apps.personal_workout_tracker.ui.AppViewModelProvider
import abs.apps.personal_workout_tracker.ui.navigation.INavigationDestination
import abs.apps.personal_workout_tracker.ui.screens.helpers.AppTopBar
import abs.apps.personal_workout_tracker.ui.screens.workouts.WorkoutDatesDestination
import abs.apps.personal_workout_tracker.ui.screens.workouts.WorkoutItem
import abs.apps.personal_workout_tracker.ui.viewmodels.dataUI.toTraining
import abs.apps.personal_workout_tracker.ui.viewmodels.dataUI.toWorkout
import abs.apps.personal_workout_tracker.ui.viewmodels.trainings.TrainingDatesViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

object TrainingDatesDestination : INavigationDestination {
    override val route = "training_dates"
    override val titleRes = R.string.training_dates
    const val trainingIdArg = "trainingId"
    val routeWithArgs = "$route/{$trainingIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingDatesScreen(
    onNavigateUp: () -> Unit,
    viewModel: TrainingDatesViewModel = viewModel(factory = AppViewModelProvider.Factory),
    trainingId: Int
) {
    LaunchedEffect(trainingId) {
        viewModel.getDatesForOneTraining(trainingId)
        viewModel.getTraining(trainingId)
    }
    val timestamps by viewModel.timestamps.collectAsState()
    val training = viewModel.training.toTraining()
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
            TrainingItem(
                training = training,
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
