package abs.apps.personal_workout_tracker.ui.screens

import abs.apps.personal_workout_tracker.R
import abs.apps.personal_workout_tracker.ui.navigation.INavigationDestination
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


object StartDestination : INavigationDestination {
    override val route = "start"
    override val titleRes = R.string.app_name
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(
    navigateToWorkouts: () -> Unit,
    navigateToTrainings: () -> Unit,
    navigateToSynchronization: () -> Unit
) {
    Scaffold()
    { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            LargeTileButton(text = "Show all workouts", onClick = navigateToWorkouts)
            Spacer(modifier = Modifier.height(16.dp))
            LargeTileButton(text = "Show all trainings", onClick = navigateToTrainings)
            Spacer(modifier = Modifier.height(16.dp))
            LargeTileButton(text = "Synchronize",
                onClick = {
                }
            )
        }

    }
}


@Composable
fun LargeTileButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 150.dp)
    ) {
        Text(text = text, fontSize = 20.sp)
    }
}