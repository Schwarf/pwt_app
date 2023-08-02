package abs.apps.personal_workout_tracker.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay

@Composable
fun StartupScreen(navigateToHome: () -> Unit)
{
    LaunchedEffect(Unit)
    {
        delay(1000L)
        navigateToHome
    }
}