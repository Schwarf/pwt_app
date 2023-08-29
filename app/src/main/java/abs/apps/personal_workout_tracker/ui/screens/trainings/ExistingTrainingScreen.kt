package abs.apps.personal_workout_tracker.ui.screens.trainings

import abs.apps.personal_workout_tracker.R
import abs.apps.personal_workout_tracker.ui.AppViewModelProvider
import abs.apps.personal_workout_tracker.ui.navigation.INavigationDestination
import abs.apps.personal_workout_tracker.ui.screens.helpers.AppTopBar
import abs.apps.personal_workout_tracker.ui.viewmodels.trainings.ExistingTraining
import abs.apps.personal_workout_tracker.ui.viewmodels.trainings.ExistingTrainingViewModel
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

object ExistingTrainingDestination : INavigationDestination {
    override val route = "existing_training"
    override val titleRes = R.string.training_exists
    const val trainingIdArg = "trainingId"
    val routeWithArgs = "$route/{$trainingIdArg}"
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ExistingTrainingScreen(
    navigateToEditTraining: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ExistingTrainingViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val state by viewModel.existingTrainingsState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(ExistingTrainingDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToEditTraining(state.trainingUI.id) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))

            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.edit_training_title),
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        ExistingTrainingBody(
            existingTraining = state,
            onAddPerformance = { viewModel.addOnePerformance() },
            onRemovePerformance = { viewModel.removeOnePerformance() },
            onDelete = {
                coroutineScope.launch {
                    viewModel.deleteTraining()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        )
    }
}

@Composable
private fun ExistingTrainingBody(
    existingTraining: ExistingTraining,
    onAddPerformance: () -> Unit,
    onRemovePerformance: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
        var removePerformanceConfirmationRequired by rememberSaveable { mutableStateOf(false) }
        ExistingTrainingDetails(existingTraining = existingTraining)
        Button(
            onClick = onAddPerformance,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            enabled = true
        ) {
            Text(stringResource(R.string.add_performance))
        }
        Button(
            onClick = { removePerformanceConfirmationRequired = true },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            enabled = (existingTraining.trainingUI.performances.toIntOrNull() ?: 0) > 0
        ) {
            Text(stringResource(R.string.remove_performance))
        }


        OutlinedButton(
            onClick = { deleteConfirmationRequired = true },
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.delete))
        }
        if (deleteConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    onDelete()
                },
                onDeleteCancel = { deleteConfirmationRequired = false },
                deleteQuestionResID = R.string.delete_question,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            )
        }

        if (removePerformanceConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    removePerformanceConfirmationRequired = false
                    onRemovePerformance()
                },
                onDeleteCancel = { removePerformanceConfirmationRequired = false },
                deleteQuestionResID = R.string.remove_performance_question,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            )
        }


    }
}

@Composable
fun ExistingTrainingDetails(
    existingTraining: ExistingTraining,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
        ) {
            ExistingTrainingRow(
                labelResID = R.string.training_name,
                value = existingTraining.trainingUI.name,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen
                            .padding_medium
                    )
                )
            )
            ExistingTrainingRow(
                labelResID = R.string.training_duration,
                value = existingTraining.trainingUI.durationInMinutes,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen
                            .padding_medium
                    )
                )
            )
            val lastPerformanceDateTime: String =
                " (last: " + existingTraining.timestampUI.dateTimeString + ")"
            ExistingTrainingRow(
                labelResID = R.string.training_performances,
                value = existingTraining.trainingUI.performances,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen
                            .padding_medium
                    )
                ),
                extraDescription = lastPerformanceDateTime
            )

        }
    }
}


@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit, onDeleteCancel: () -> Unit, @StringRes deleteQuestionResID: Int,
    modifier: Modifier = Modifier,

    ) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        title = { Text(stringResource(R.string.attention)) },
        text = { Text(stringResource(deleteQuestionResID)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = stringResource(R.string.no))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = stringResource(R.string.yes))
            }
        })
}


@Composable
private fun ExistingTrainingRow(
    @StringRes labelResID: Int, value: String, modifier: Modifier = Modifier,
    extraDescription: String = ""
) {
    Row(modifier = modifier) {
        Text(text = stringResource(labelResID))
        if (extraDescription.isNotBlank()) {
            Text(text = extraDescription, style = MaterialTheme.typography.labelSmall)
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(text = value, fontWeight = FontWeight.Bold)
    }
}
