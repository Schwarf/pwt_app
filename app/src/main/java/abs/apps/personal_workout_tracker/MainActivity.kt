package abs.apps.personal_workout_tracker

import abs.apps.personal_workout_tracker.ui.theme.Personal_workout_trackerTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
                        //WorkoutListScreen()
                        if(showWorkoutInputMask.value)
                        {
                            WorkoutListScreen();
                        }
                        else
                        {
                            AddButton("Add Workout") {
                            showWorkoutInputMask.value = true
                        }
                        }
                        //AddButton("Add Set")
                        //AddButton("Add Schedule")
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
fun WorkoutListScreen()
{
    val workoutList = remember { mutableStateListOf<String>()}
    Column(modifier = Modifier.padding(16.dp)){
        WorkoutList(workoutList)
        AddWorkoutInput{workoutName -> workoutList.add(workoutName)
        }
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
fun AddWorkoutInput(onAddWorkout: (String) -> Unit) {
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