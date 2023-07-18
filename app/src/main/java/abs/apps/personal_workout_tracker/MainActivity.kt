package abs.apps.personal_workout_tracker

import abs.apps.personal_workout_tracker.ui.screens_and_dialogs.WorkoutScreen
import abs.apps.personal_workout_tracker.ui.theme.Personal_workout_trackerTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room

class MainActivity : ComponentActivity() {

    private val database by lazy {
        Room.databaseBuilder(applicationContext, WorkoutDatabase::class.java, "workout.db").build()
    }

    private val viewModel by viewModels<WorkoutViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return WorkoutViewModel(database.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Personal_workout_trackerTheme {
                val state by viewModel.state.collectAsState()
                WorkoutScreen(state = state, onEvent = viewModel::onEvent)


            }
        }
    }
}
/*
    private enum class Screen {
        DEFAULT_SCREEN,
        WORKOUT_SELECTION,
        WORKOUT_INPUT
    }

    private val currentScreen = mutableStateOf(MainActivity.Screen.DEFAULT_SCREEN)
    private val workouts = Workouts()
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
                        if (currentScreen.value == MainActivity.Screen.WORKOUT_INPUT) {
                            workouts.AddScreen { currentScreen.value =
                                MainActivity.Screen.DEFAULT_SCREEN
                            }
                        }
                        if (currentScreen.value == MainActivity.Screen.WORKOUT_SELECTION) {
                            workouts.ChooseScreen(onWorkoutSelected = {
                                currentScreen.value = MainActivity.Screen.DEFAULT_SCREEN
                            },
                                onItemSelectionCancelled = {
                                    currentScreen.value = MainActivity.Screen.DEFAULT_SCREEN
                                })
                        }
                        if (currentScreen.value == MainActivity.Screen.DEFAULT_SCREEN) {
                            workouts.AddButton("Add Workout") {
                                currentScreen.value = MainActivity.Screen.WORKOUT_INPUT
                            }
                            workouts.AddButton("Open Workout Selection") {
                                currentScreen.value = MainActivity.Screen.WORKOUT_SELECTION
                            }
                        }

                    }
                }
            }
        }
    }
}


*/