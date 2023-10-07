package abs.apps.personal_workout_tracker.ui.screens

import abs.apps.personal_workout_tracker.R
import abs.apps.personal_workout_tracker.ui.navigation.INavigationDestination
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable

object SynchronizationDestination : INavigationDestination {
    override val route = "synchronization"
    override val titleRes = R.string.synchronization
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SynchronizationScreen(navigateToStartScreen: () -> Unit) {

}
