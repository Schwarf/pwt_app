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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class Workouts {
    data class WorkoutEntry(
        var workout: String,
        var sets: Int,
        var totalRepetitions: Int,
        var maxRepetitionsInSets: Int
    )

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
            Text(text = workout.totalRepetitions.toString(), modifier = Modifier.weight(1f))
            Text(text = workout.maxRepetitionsInSets.toString(), modifier = Modifier.weight(1f))
        }
    }

    @Composable
    fun WorkoutHeaderRow() {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Workout", modifier = Modifier.weight(1f))
            Text(text = "Sets", modifier = Modifier.weight(1f))
            Text(text = "Total reps", modifier = Modifier.weight(1f))
            Text(text = "Max reps in one set", modifier = Modifier.weight(1f))
        }
    }

    object FilteredDigitsTransformation : VisualTransformation {
        override fun filter(text: AnnotatedString): TransformedText {
            val digitsOnly = text.text.filter { it.isDigit() }
            return TransformedText(AnnotatedString(digitsOnly), OffsetMapping.Identity)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AddItem(onAddWorkout: (WorkoutEntry) -> Unit) {
        // Local state to hold the input value
        val workoutName = remember { mutableStateOf("") }
        val sets = remember { mutableStateOf<Int?>(null) }
        val totalRepetitions = remember { mutableStateOf<Int?>(null) }
        val maxRepetitionsInSets = remember { mutableStateOf<Int?>(null) }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Input field for the workout name
            TextField(
                value = workoutName.value,
                onValueChange = { workoutName.value = it },
//                label = { Text("Workout") },
                modifier = Modifier.weight(1f)
            )
            DigitsOnlyTextField(
                value = sets.value,
                onValueChange = { newValue -> sets.value = newValue },
                modifier = Modifier.weight(1f)
            )
            DigitsOnlyTextField(
                value = totalRepetitions.value,
                onValueChange = { newValue -> totalRepetitions.value = newValue },
                modifier = Modifier.weight(1f)
            )
            DigitsOnlyTextField(
                value = maxRepetitionsInSets.value,
                onValueChange = { newValue -> maxRepetitionsInSets.value = newValue },
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
                    val workout = workoutName.value
                    if (workout.isNotEmpty()) {
                        // Add the workout name to the list
                        onAddWorkout(
                            WorkoutEntry(
                                workout,
                                sets.value ?: 0,
                                totalRepetitions.value ?: 0,
                                maxRepetitionsInSets.value ?: 0
                            )
                        )
                    }

                    // Clear the input fields
                    workoutName.value = ""
                    sets.value = 0
                    totalRepetitions.value = 0
                    maxRepetitionsInSets.value = 0
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(text = "Add Workout")
            }
        }

    }

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
            visualTransformation = FilteredDigitsTransformation,
        )
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
                modifier = Modifier.weight(1f),
            )
            DigitsOnlyTextField(
                value = editedWorkoutEntry.value.sets,
                onValueChange = { newValue ->
                    editedWorkoutEntry.value =
                        editedWorkoutEntry.value.copy(sets = newValue ?: 0)
                },
                modifier = Modifier.weight(1f)
            )
            DigitsOnlyTextField(
                value = editedWorkoutEntry.value.totalRepetitions,
                onValueChange = { newValue ->
                    editedWorkoutEntry.value =
                        editedWorkoutEntry.value.copy(totalRepetitions = newValue ?: 0)
                },
                modifier = Modifier.weight(1f)
            )
            DigitsOnlyTextField(
                value = editedWorkoutEntry.value.maxRepetitionsInSets,
                onValueChange = { newValue ->
                    editedWorkoutEntry.value =
                        editedWorkoutEntry.value.copy(maxRepetitionsInSets = newValue ?: 0)
                },
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
                Text(text = "Save Workout")
            }
            Button(
                onClick = {
                    if (editedWorkoutEntry.value.workout.isNotEmpty()) {
                        onWorkoutEdited(editedWorkoutEntry.value)
                        listOfWorkouts.remove(editedWorkoutEntry.value)
                    }
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(text = "Delete Workout")
            }
        }
    }
}