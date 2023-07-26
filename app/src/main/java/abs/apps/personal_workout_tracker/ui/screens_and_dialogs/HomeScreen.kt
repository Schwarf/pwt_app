package abs.apps.personal_workout_tracker.ui.screens_and_dialogs

import abs.apps.personal_workout_tracker.R
import abs.apps.personal_workout_tracker.Workout
import abs.apps.personal_workout_tracker.WorkoutEvent
import abs.apps.personal_workout_tracker.WorkoutState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * App bar to display title and conditionally display the back navigation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: WorkoutState,
    modifier: Modifier = Modifier,
    onEvent: (WorkoutEvent) -> Unit
) {
    val chosenWorkout = remember { mutableStateOf<Workout?>(null) }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            WorkoutTopAppBar(
                title = stringResource(R.string.app_name),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton =
        {
            FloatingActionButton(onClick = { onEvent(WorkoutEvent.ShowAddDialog) }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_workout)
                )
            }
        },
        //modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
    ) { padding ->
        if (state.isAddingWorkout) {
            AddWorkoutDialog(state = state, onEvent = onEvent)
        }
        if (state.isChoosingAction) {
            ChooseActionDialog(workout = chosenWorkout.value!!, onEvent = onEvent)
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
                        Text(
                            text = stringResource(id = R.string.workout_name),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = stringResource(id = R.string.workout_sets),
                            textAlign = TextAlign.End,
                            modifier = Modifier
                                .weight(1f)
                        )
                        Text(
                            text = stringResource(id = R.string.workout_totalReps),
                            textAlign = TextAlign.End,
                            modifier = Modifier
                                .weight(1f)
                        )
                        Text(
                            text = stringResource(id = R.string.workout_maxRepsInSets),
                            textAlign = TextAlign.End,
                            modifier = Modifier
                                .weight(1f)
                        )
                        IconButton(
                            onClick = {}, modifier = Modifier
                                .alpha(0f)
                                .weight(1f)
                        )
                        {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(id = R.string.delete)
                            )
                        }
                    }
                }
                items(state.workouts) { workout ->
                    Button(
                        onClick = {
                            onEvent(WorkoutEvent.ShowChooseActionDialog)
                            chosenWorkout.value = workout
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Blue
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    {
                        WorkoutEntryRow(workout = workout, onEvent = onEvent)
                    }
                }
            },
            contentPadding = padding,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)

        )
    }
}

