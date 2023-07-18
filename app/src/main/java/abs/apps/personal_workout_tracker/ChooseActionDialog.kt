package abs.apps.personal_workout_tracker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
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
                Text(text = "Edit Workout")
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
            WorkoutEntryRow(workout = workout , onEvent = onEvent, hasDeleteIcon = false)
            
        }
    )
}