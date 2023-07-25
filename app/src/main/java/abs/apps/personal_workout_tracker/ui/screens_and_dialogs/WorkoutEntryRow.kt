package abs.apps.personal_workout_tracker.ui.screens_and_dialogs

import abs.apps.personal_workout_tracker.Workout
import abs.apps.personal_workout_tracker.WorkoutEvent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun WorkoutEntryRow(
    workout: Workout,
    onEvent: (WorkoutEvent) -> Unit,
    hasDeleteIcon: Boolean = true
) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(text = workout.name, modifier = Modifier.weight(2f))
        Text(
            text = workout.sets,
            modifier = Modifier
                .weight(1f),
            textAlign = TextAlign.Center
        )
        Text(
            text = workout.totalRepetitions,
            modifier = Modifier
                .weight(1f),
            textAlign = TextAlign.Center
        )
        Text(
            text = workout.maxRepetitionsInSets,
            modifier = Modifier
                .weight(1f),
            textAlign = TextAlign.Center
        )
        if (hasDeleteIcon) {
            IconButton(
                onClick = { onEvent(WorkoutEvent.DeleteWorkout(workout)) },
                modifier = Modifier
                    .weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete workout"
                )
            }
        }
    }
}
