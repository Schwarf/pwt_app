package abs.apps.personal_workout_tracker.ui.screens

import abs.apps.personal_workout_tracker.R
import abs.apps.personal_workout_tracker.WorkoutEvent
import abs.apps.personal_workout_tracker.ui.AppViewModelProvider
import abs.apps.personal_workout_tracker.ui.navigation.INavigationDestination
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.util.Currency
import java.util.Locale

object WorkoutEntryDestination : INavigationDestination {
    override val route = "item_entry"
    override val titleRes = R.string.add_workout
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWorkoutDialog(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: AddWorkoutViewModel = viewModel(factory=AppViewModelProvider.Factory)
    ) {
    AlertDialog(
        confirmButton = {
            Button(onClick = {
                onEvent(WorkoutEvent.SaveWorkout)
            })
            {
                Text(text = stringResource(R.string.save))
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
                        Text(text = stringResource(id = R.string.workout_name_required))
                    }
                )
                TextField(
                    value = state.sets,
                    onValueChange = {
                        onEvent(WorkoutEvent.SetSets(it))
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.workout_sets_required))
                    }
                )
                TextField(
                    value = state.totalRepetitions,
                    onValueChange = {
                        onEvent(WorkoutEvent.SetTotalRepetitions(it))
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.workout_totalReps_required))
                    }
                )
                TextField(
                    value = state.maxRepetitionsInSet,
                    onValueChange = {
                        onEvent(WorkoutEvent.SetMaxRepetitionsInSet(it))
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.workout_maxRepsInSets_required))
                    }
                )

            }

        }
    )
}

@Composable

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun WorkoutInputForm(
    workoutDetails: WorkoutDetails,
    modifier: Modifier = Modifier,
    onValueChange: (WorkoutDetails) -> Unit = {},
    enabled: Boolean = true
)
{
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        OutlinedTextField(
            value = workoutDetails.name,
            onValueChange = { onValueChange(workoutDetails.copy(name = it)) },
            label = { Text(stringResource(R.string.workout_name_required)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = workoutDetails.sets,
            onValueChange = { onValueChange(workoutDetails.copy(sets = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            label = { Text(stringResource(R.string.workout_sets_required)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            leadingIcon = { Text(Currency.getInstance(Locale.getDefault()).symbol) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = workoutDetails.totalRepetitions,
            onValueChange = { onValueChange(workoutDetails.copy(totalRepetitions = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(stringResource(R.string.workout_totalReps_required)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = workoutDetails.maxRepetitionsInSet,
            onValueChange = { onValueChange(workoutDetails.copy(maxRepetitionsInSet = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(stringResource(R.string.workout_totalReps_required)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        if (enabled) {
            Text(
                text = stringResource(R.string.required_fields),
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }

}