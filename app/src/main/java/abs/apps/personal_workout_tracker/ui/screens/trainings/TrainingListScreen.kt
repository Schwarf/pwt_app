package abs.apps.personal_workout_tracker.ui.screens.trainings

import abs.apps.personal_workout_tracker.R
import abs.apps.personal_workout_tracker.data.database.Training
import abs.apps.personal_workout_tracker.ui.AppViewModelProvider
import abs.apps.personal_workout_tracker.ui.navigation.INavigationDestination
import abs.apps.personal_workout_tracker.ui.screens.helpers.ListScreenTopBar
import abs.apps.personal_workout_tracker.ui.viewmodels.trainings.TrainingListScreenViewModel
import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

object TrainingListDestination : INavigationDestination {
    override val route = "training_list"
    override val titleRes = R.string.app_name
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TrainingListScreen(
    navigateToAddTraining: () -> Unit,
    navigateToExistingTraining: (Int) -> Unit,
    navigateToOtherList: () ->Unit,
    modifier: Modifier = Modifier,
    viewModel: TrainingListScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val state by viewModel.listOfTrainingsState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ListScreenTopBar(
                showWorkouts = false,
                navigateToOtherList = navigateToOtherList,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton =
        {
            FloatingActionButton(onClick = navigateToAddTraining) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_training)
                )
            }
        },
    ) { padding ->

        TrainingListBody(
            trainingList = state.trainingList,
            onItemClick = navigateToExistingTraining,
            modifier = modifier
                .padding(padding)
                .fillMaxSize(),
            onAddPerformance = { trainingId -> viewModel.addPerformance(trainingId) }
        )
    }
}

@Composable
private fun TrainingListBody(
    trainingList: List<Training>, onItemClick: (Int) -> Unit, modifier: Modifier = Modifier,
    onAddPerformance: (Int) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if (trainingList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_trainings_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        } else {
            TrainingList(
                itemList = trainingList,
                onItemClick = { onItemClick(it.id) },
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small)),
                onAddPerformance = onAddPerformance
            )
        }
    }
}

@Composable
private fun TrainingList(
    itemList: List<Training>, onItemClick: (Training) -> Unit, modifier: Modifier = Modifier,
    onAddPerformance: (Int) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(items = itemList, key = { it.id }) { item ->
            TrainingItem(
                training = item,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_extra_small))
                    .clickable { onItemClick(item) },
                onAddPerformance = onAddPerformance
            )
        }
    }
}

@Composable
fun TrainingItem(
    training: Training,
    modifier: Modifier = Modifier,
    onAddPerformance: ((Int) -> Unit)? = null
) {

    Card(
        modifier = modifier, elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                // This ensures elements take as much space as they can
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = training.name,
                    style = MaterialTheme.typography.titleLarge,
                    // This makes the text take up all available space but no more
                    modifier = Modifier.weight(1f)
                )
                // Nested row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = stringResource(
                            id = R.string.performed,
                            training.performances
                        ),
                        color = Color.Gray,
                        style = MaterialTheme.typography.titleMedium
                    )
                    // Space between Text and IconButton
                    Spacer(Modifier.width(8.dp))
                    if(onAddPerformance != null) {
                        Surface(
                            shape = CircleShape,
                            color = Color.Gray,
                            modifier = Modifier
                                .size(48.dp)
                        ) {
                            IconButton(
                                onClick = { onAddPerformance(training.id) }
                            ) {
                                Icon(Icons.Default.Add, contentDescription = "Add")
                            }
                        }
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(
                        id = R.string.set_training_duration,
                        training.durationMinutes
                    ),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}
