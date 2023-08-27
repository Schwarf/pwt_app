package abs.apps.personal_workout_tracker.ui.screens

import abs.apps.personal_workout_tracker.R
import abs.apps.personal_workout_tracker.data.database.Workout
import abs.apps.personal_workout_tracker.ui.AppViewModelProvider
import abs.apps.personal_workout_tracker.ui.navigation.INavigationDestination
import abs.apps.personal_workout_tracker.ui.screens.helpers.AppTopBar
import abs.apps.personal_workout_tracker.ui.screens.helpers.HomeScreenTopBar
import abs.apps.personal_workout_tracker.ui.viewmodels.HomeViewModel
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


object HomeDestination : INavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navigateToAddWorkout: () -> Unit,
    navigateToExistingWorkout: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val state by viewModel.listOfWorkoutsState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HomeScreenTopBar(
                addWorkoutButtonEnabled = true,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton =
        {
            FloatingActionButton(onClick = navigateToAddWorkout) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_workout)
                )
            }
        },
    ) { padding ->

        HomeBody(
            workoutList = state.workoutList,
            onItemClick = navigateToExistingWorkout,
            modifier = modifier
                .padding(padding)
                .fillMaxSize(),
            onAddPerformance = { workoutId -> viewModel.addPerformance(workoutId) }
        )
    }
}

@Composable
private fun HomeBody(
    workoutList: List<Workout>, onItemClick: (Int) -> Unit, modifier: Modifier = Modifier,
    onAddPerformance: (Int) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if (workoutList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_workout_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        } else {
            WorkoutList(
                itemList = workoutList,
                onItemClick = { onItemClick(it.id) },
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small)),
                onAddPerformance = onAddPerformance
            )
        }
    }
}

@Composable
private fun WorkoutList(
    itemList: List<Workout>, onItemClick: (Workout) -> Unit, modifier: Modifier = Modifier,
    onAddPerformance: (Int) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(items = itemList, key = { it.id }) { item ->
            WorkoutItem(
                workout = item,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_extra_small))
                    .clickable { onItemClick(item) },
                onAddPerformance = onAddPerformance
            )
        }
    }
}

@Composable
private fun WorkoutItem(
    workout: Workout,
    modifier: Modifier = Modifier,
    onAddPerformance: (Int) -> Unit
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
                    text = workout.name,
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
                            id = R.string.workout_performances_home,
                            workout.performances
                        ),
                        color = Color.Gray,
                        style = MaterialTheme.typography.titleMedium
                    )
                    // Space between Text and IconButton
                    Spacer(Modifier.width(8.dp))
                    Surface(
                        shape = CircleShape,
                        color = Color.Gray,
                        modifier = Modifier
                            .size(48.dp)
                    ) {
                        IconButton(
                            onClick = { onAddPerformance(workout.id) }
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Add")
                        }
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.set_workout_sets, workout.sets),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = stringResource(
                        id = R.string.set_workout_totalReps,
                        workout.totalRepetitions
                    ),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(
                        id = R.string.set_workout_maxRepsInSets,
                        workout.maxRepetitionsInSet
                    ),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}
