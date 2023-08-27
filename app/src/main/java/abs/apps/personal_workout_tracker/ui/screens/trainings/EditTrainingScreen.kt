package abs.apps.personal_workout_tracker.ui.screens.trainings


import abs.apps.personal_training_tracker.ui.screens.helpers.TrainingInputBody
import abs.apps.personal_workout_tracker.R
import abs.apps.personal_workout_tracker.ui.AppViewModelProvider
import abs.apps.personal_workout_tracker.ui.navigation.INavigationDestination
import abs.apps.personal_workout_tracker.ui.screens.helpers.AppTopBar
import abs.apps.personal_workout_tracker.ui.viewmodels.trainings.EditTrainingViewModel
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

object EditTrainingDestination : INavigationDestination {
    override val route = "training_edit"
    override val titleRes = R.string.edit_training_title
    const val trainingIdArg = "trainingId"
    val routeWithArgs = "$route/{$trainingIdArg}"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTrainingScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditTrainingViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(EditTrainingDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->
        TrainingInputBody(
            validatedTrainingUIState = viewModel.state,
            onTrainingValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateTraining()
                    navigateBack()
                }
            },
            buttonDescription = stringResource(id = R.string.update_training),
            modifier = Modifier.padding(innerPadding)
        )
    }
}
