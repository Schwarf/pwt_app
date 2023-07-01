package abs.apps.personal_workout_tracker

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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class Workouts {
    data class WorkoutEntry(var workout: String, var sets: Int, var repetitions: Int)

    private val listOfWorkouts = mutableStateListOf<WorkoutEntry>()
    private val selectedWorkoutEntry = mutableStateOf<WorkoutEntry?>(null)

    @Composable
    fun AddButton(label: String, onClick: () -> Unit) {
        Button(onClick = onClick, modifier = Modifier.padding(90.dp)) {
            Text(text = label, fontSize = 20.sp)
        }
    }


    @Composable
    fun ChooseScreen(
        onWorkoutSelected: (String) -> Unit,
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
                item { WorkoutHeaderRow() }
                items(listOfWorkouts) { workout ->
                    WorkoutEntryRow(workout = workout)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            )
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
    fun AddScreen(onReturn: () -> Unit) {
        Column(modifier = Modifier.padding(16.dp)) {
            WorkoutList(onEditWorkout = {})
            AddItem { entry ->
                listOfWorkouts.add(entry)
            }
        }
        Button(onClick = onReturn, modifier = Modifier.padding(16.dp)) {
            Text(text = "Finish")
        }

    }

    @Composable
    fun WorkoutList(onEditWorkout: (WorkoutEntry) -> Unit) {

        LazyColumn {
            // Display each workout in the list
            item { WorkoutHeaderRow() }
            items(listOfWorkouts) { workout ->
                Button(
                    onClick = { selectedWorkoutEntry.value = workout },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Blue
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                {
                    WorkoutEntryRow(workout = workout)
                }
            }

        }
        val selectedEntry = selectedWorkoutEntry.value
        if (selectedEntry != null) {
            EditItem(entry = selectedEntry,
                onWorkoutEdited = { editedEntry ->
                    onEditWorkout(editedEntry)
                    selectedWorkoutEntry.value = null
                })
        }
    }


    @Composable
    fun WorkoutEntryRow(workout: WorkoutEntry) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = workout.workout, modifier = Modifier.weight(1f))
            Text(text = workout.sets.toString(), modifier = Modifier.weight(1f))
            Text(text = workout.repetitions.toString(), modifier = Modifier.weight(1f))
        }
    }

    @Composable
    fun WorkoutHeaderRow() {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Workout", modifier = Modifier.weight(1f))
            Text(text = "Sets", modifier = Modifier.weight(1f))
            Text(text = "Repetitions", modifier = Modifier.weight(1f))
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AddItem(onAddWorkout: (WorkoutEntry) -> Unit) {
        // Local state to hold the input value
        val workoutNameState = remember { mutableStateOf("") }
        val setsState = remember { mutableStateOf(0) }
        val repetitionState = remember { mutableStateOf(0) }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Input field for the workout name
            TextField(
                value = workoutNameState.value,
                onValueChange = { workoutNameState.value = it },
//                label = { Text("Workout") },
                modifier = Modifier.weight(1f)
            )
            TextField(
                value = setsState.value.toString(),
                onValueChange = { newValue -> setsState.value = newValue.toIntOrNull() ?: 0 },
//                label = { Text("Sets") },
                modifier = Modifier.weight(1f)
            )
            TextField(
                value = repetitionState.value.toString(),
                onValueChange = { newValue -> repetitionState.value = newValue.toIntOrNull() ?: 0 },
//                label = { Text("Repetitions") },
                modifier = Modifier.weight(1f)
            )


        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Button to add the workout
            Button(
                onClick = {
                    // Get the workout name from the input field
                    val workoutName = workoutNameState.value
                    if (workoutName.isNotEmpty()) {
                        // Add the workout name to the list
                        onAddWorkout(
                            WorkoutEntry(
                                workoutNameState.value,
                                setsState.value,
                                repetitionState.value
                            )
                        )
                    }

                    // Clear the input field
                    workoutNameState.value = ""
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(text = "Add Workout")
            }
        }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun EditItem(entry: WorkoutEntry, onWorkoutEdited: (WorkoutEntry) -> Unit) {
        // Local state to hold the input value
        val editedWorkoutEntry = remember { mutableStateOf(entry.copy()) }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Input field for the workout name
            TextField(
                value = editedWorkoutEntry.value.workout,
                onValueChange = {
                    editedWorkoutEntry.value = editedWorkoutEntry.value.copy(workout = it)
                },
//                label = { Text("Workout") },
                modifier = Modifier.weight(1f)
            )
            TextField(
                value = editedWorkoutEntry.value.sets.toString(),
                onValueChange = { newValue ->
                    editedWorkoutEntry.value =
                        editedWorkoutEntry.value.copy(sets = newValue.toIntOrNull() ?: 0)
                },
//                label = { Text("Sets") },
                modifier = Modifier.weight(1f)
            )
            TextField(
                value = editedWorkoutEntry.value.repetitions.toString(),
                onValueChange = { newValue ->
                    editedWorkoutEntry.value =
                        editedWorkoutEntry.value.copy(repetitions = newValue.toIntOrNull() ?: 0)
                },
//                label = { Text("Repetitions") },
                modifier = Modifier.weight(1f)
            )


        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Button to add the workout
            Button(
                onClick = {
                    if (editedWorkoutEntry.value.workout.isNotEmpty()) {
                        onWorkoutEdited(editedWorkoutEntry.value)
                        listOfWorkouts[listOfWorkouts.indexOf(entry)] = editedWorkoutEntry.value
                    }
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(text = "Change Workout")
            }
        }

    }

}