package abs.apps.personal_workout_tracker

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen(
    state: WorkoutState,
    onEvent: (WorkoutEvent) -> Unit
) {
    Scaffold(
        floatingActionButton =
        {
            FloatingActionButton(onClick = { onEvent(WorkoutEvent.ShowDialog) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Workout")
            }
        },
        modifier = Modifier.padding(16.dp)
    ){padding ->{}

    }
