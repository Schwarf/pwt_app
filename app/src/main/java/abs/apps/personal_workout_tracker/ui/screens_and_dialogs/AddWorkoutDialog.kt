package abs.apps.personal_workout_tracker.ui.screens_and_dialogs

import abs.apps.personal_workout_tracker.R
import abs.apps.personal_workout_tracker.WorkoutEvent
import abs.apps.personal_workout_tracker.WorkoutState
import abs.apps.personal_workout_tracker.Workouts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DigitsOnlyTextField(
    value: Int?,
    onValueChange: (Int?) -> Unit,
    modifier: Modifier = Modifier
) {
    val displayValue = value?.toString() ?: ""
    TextField(
        value = if (displayValue == "0") "" else displayValue,
        onValueChange = { newValue ->
            onValueChange(newValue.toIntOrNull())
        },
        modifier = modifier,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        visualTransformation = Workouts.FilteredDigitsTransformation,
        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWorkoutDialog(
    state: WorkoutState,
    onEvent: (WorkoutEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        confirmButton = {
            Button(onClick = {
                onEvent(WorkoutEvent.SaveWorkout)
            })
            {
                Text(text = stringResource(R.string.save_workout))
            }
        },
        modifier = modifier,
        onDismissRequest = {
            onEvent(WorkoutEvent.HideAddDialog)
        },
        title = { Text(text = stringResource(R.string.add_workout)) },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = state.name,
                    onValueChange = {
                        onEvent(WorkoutEvent.SetName(it))
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.workout_name))
                    }
                )
                TextField(
                    value = state.sets,
                    onValueChange = {
                        onEvent(WorkoutEvent.SetSets(it))
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.workout_sets))
                    }
                )
                TextField(
                    value = state.totalRepetitions,
                    onValueChange = {
                        onEvent(WorkoutEvent.SetTotalRepetitions(it))
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.workout_totalReps))
                    }
                )
                TextField(
                    value = state.maxRepetitionsInSet,
                    onValueChange = {
                        onEvent(WorkoutEvent.SetMaxRepetitionsInSet(it))
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.workout_maxRepsInSets))
                    }
                )

            }

        }
    )
}