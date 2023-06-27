package abs.apps.personal_workout_tracker

import abs.apps.personal_workout_tracker.ui.theme.Personal_workout_trackerTheme
import android.net.wifi.hotspot2.pps.Credential.SimCredential
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

import abs.apps.personal_workout_tracker.Workouts

class MainActivity : ComponentActivity() {
    private enum class Screen {
        DEFAULT_SCREEN,
        WORKOUT_SELECTION,
        WORKOUT_INPUT
    }

    private val currentScreen = mutableStateOf(Screen.DEFAULT_SCREEN)
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
                        if (currentScreen.value == Screen.WORKOUT_INPUT) {
                            workouts.AddScreen { currentScreen.value = Screen.DEFAULT_SCREEN };
                        }
                        if (currentScreen.value == Screen.WORKOUT_SELECTION) {
                            workouts.ChooseScreen(onWorkoutSelected = {
                                currentScreen.value = Screen.DEFAULT_SCREEN
                            },
                                onItemSelectionCancelled = {
                                    currentScreen.value = Screen.DEFAULT_SCREEN
                                })
                        }
                        if (currentScreen.value == Screen.DEFAULT_SCREEN) {
                            workouts.AddButton("Add Workout") {
                                currentScreen.value = Screen.WORKOUT_INPUT
                            }
                            workouts.AddButton("Open Workout Selection") {
                                currentScreen.value = Screen.WORKOUT_SELECTION
                            }
                        }

                    }
                }
            }
        }
    }
}


