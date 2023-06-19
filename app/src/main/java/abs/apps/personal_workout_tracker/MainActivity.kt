package abs.apps.personal_workout_tracker

import abs.apps.personal_workout_tracker.ui.theme.Personal_workout_trackerTheme
import android.os.Bundle
import androidx.preference.PreferenceDataStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    private val showWorkoutInputMask = mutableStateOf(false);
    private val listOfWorkouts = mutableStateListOf<String>();
    private val showWorkoutSelection = mutableStateOf(false)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Personal_workout_trackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (showWorkoutInputMask.value) {
                            AddWorkoutScreen(listOfWorkouts) { showWorkoutInputMask.value = false };
                        } else {
                            AddButton("Add Workout") {
                                showWorkoutInputMask.value = true
                                showWorkoutSelection.value = false
                            }
                        }
                        if (showWorkoutSelection.value) {
                            ChooseWorkoutScreen(listOfWorkouts = listOfWorkouts,
                                onWorkoutSelected = { selectedItem ->
                                    showWorkoutSelection.value = false
                                },
                                onItemSelectionCancelled = { showWorkoutSelection.value = false })
                        } else {
                            AddButton("Open Workout Selection") {
                                showWorkoutSelection.value = true
                                showWorkoutInputMask.value = false
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun AddButton(label: String, onClick: () -> Unit) {
    Button(onClick = onClick, modifier = Modifier.padding(90.dp)) {
        Text(text = label, fontSize = 20.sp)
    }
}

@Composable
fun ChooseButton(label: String, onClick: () -> Unit) {
    Button(onClick = onClick, modifier = Modifier.padding(90.dp)) {
        Text(text = label, fontSize = 20.sp)
    }
}

class WorkoutDataStore : PreferenceDataStore()
{

}

@Composable
fun ChooseWorkoutScreen(
    listOfWorkouts: List<String>, onWorkoutSelected: (String) -> Unit,
    onItemSelectionCancelled: () -> Unit
) {
    val selectedWorkout = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    )
    {
        Text(text = "Select a workout: ")
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(listOfWorkouts) { workout ->
                Text(
                    text = workout,
                    modifier = Modifier
                        .clickable { selectedWorkout.value = workout }
                        .padding(8.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween)
        {
            Button(
                onClick = onItemSelectionCancelled,
                modifier = Modifier.padding(8.dp)
            )
            {
                Text(text = "Cancel")
            }
        }

        Button(
            onClick = {
                if (selectedWorkout.value.isNotEmpty()) {
                    onWorkoutSelected(selectedWorkout.value)
                }
            },
            modifier = Modifier.padding(8.dp)
        ) { Text(text = "Confirm") }
    }

}


@Composable
fun AddWorkoutScreen(listOfWorkouts: MutableList<String>, onReturn: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        WorkoutList(listOfWorkouts)
        AddWorkoutItem { workoutName ->
            listOfWorkouts.add(workoutName)
        }
    }
    Button(onClick = onReturn, modifier = Modifier.padding(16.dp)) {
        Text(text = "Finish")
    }

}

@Composable
fun WorkoutList(workoutList: List<String>) {
    Column {
        // Display each workout in the list
        workoutList.forEach { workout ->
            Text(text = workout)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWorkoutItem(onAddWorkout: (String) -> Unit) {
    // Local state to hold the input value
    val workoutNameState = remember { mutableStateOf("") }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Input field for the workout name
        TextField(
            value = workoutNameState.value,
            onValueChange = { workoutNameState.value = it },
            label = { Text("Workout Name") },
            modifier = Modifier.weight(1f)
        )

        // Button to add the workout
        Button(
            onClick = {
                // Get the workout name from the input field
                val workoutName = workoutNameState.value

                // Add the workout name to the list
                onAddWorkout(workoutName)

                // Clear the input field
                workoutNameState.value = ""
            },
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(text = "Add Workout")
        }
    }
}