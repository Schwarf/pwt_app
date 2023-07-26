package abs.apps.personal_workout_tracker.ui.screens_and_dialogs

import abs.apps.personal_workout_tracker.R
import abs.apps.personal_workout_tracker.Workout
import abs.apps.personal_workout_tracker.WorkoutEvent
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter


@Composable
fun ChooseActionDialog(
    workout: Workout,
    onEvent: (WorkoutEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        confirmButton = {
            Button(onClick = {
                onEvent(WorkoutEvent.ShowEditDialog)
            })
            {
                Text(text = stringResource(id = R.string.edit))
            }
        },
        modifier = modifier,
        dismissButton = {
            Button(onClick = {
                onEvent(WorkoutEvent.ShowEditDialog)
            })
            {
                Text(text = "Have done this workout")
            }
        },
        onDismissRequest = {
            onEvent(WorkoutEvent.HideChooseActionDialog)
        },
        title = { Text(text = "Choose Action") },
        text = {
            WorkoutEntryRow(workout = workout, onEvent = onEvent, hasDeleteIcon = false)

        }
    )
}