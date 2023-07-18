package abs.apps.personal_workout_tracker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun WorkoutEntryRow(workout: Workout, onEvent: (WorkoutEvent) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(text = workout.name, modifier = Modifier.weight(2f))
        Text(
            text = workout.sets.toString(),
            modifier = Modifier
                .weight(1f)
                .padding(start = 3.dp),
            textAlign = TextAlign.End
        )
        Text(
            text = workout.totalRepetitions.toString(),
            modifier = Modifier
                .weight(1f)
                .padding(start = 3.dp),
            textAlign = TextAlign.End
        )
        Text(
            text = workout.maxRepetitionsInSets.toString(),
            modifier = Modifier
                .weight(1f)
                .padding(start = 3.dp),
            textAlign = TextAlign.End
        )
        IconButton(onClick = { onEvent(WorkoutEvent.DeleteWorkout(workout)) }) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete workout"
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutScreen(
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
    ) { padding ->
        if (state.isAddingWorkout) {
            AddWorkoutDialog(state = state, onEvent = onEvent)
        }
        LazyColumn(
            content = {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        verticalAlignment = CenterVertically
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = CenterVertically
                        ) {
                            Text(
                                text = "Workout",
                                modifier = Modifier.weight(2f)
                            )
                            Text(
                                text = "Sets", textAlign = TextAlign.End, modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 3.dp)
                            )
                            Text(
                                text = "Total reps", textAlign = TextAlign.End, modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 3.dp)
                            )
                            Text(
                                text = "Max reps in one set",
                                textAlign = TextAlign.End,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 3.dp)
                            )
                        }

                    }
                }
                items(state.workouts) { workout ->
                    WorkoutEntryRow(workout = workout, onEvent = onEvent)

                }
            },
            contentPadding = padding, modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)

        )
    }
}

