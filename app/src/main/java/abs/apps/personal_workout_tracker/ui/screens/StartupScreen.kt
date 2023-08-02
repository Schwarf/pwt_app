package abs.apps.personal_workout_tracker.ui.screens

import abs.apps.personal_workout_tracker.R
import abs.apps.personal_workout_tracker.ui.navigation.INavigationDestination
import abs.apps.personal_workout_tracker.ui.screens.helpers.AppTopBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.delay

object StartupDestination : INavigationDestination {
    override val route = "startup"
    override val titleRes = R.string.startup
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartupScreen(navigateToHome: () -> Unit) {
    LaunchedEffect(Unit)
    {
        delay(1000L)
        navigateToHome()
    }
    Scaffold(topBar = {
        AppTopBar(
            title = stringResource(StartupDestination.titleRes),
            canNavigateBack = false,
        )
    }
    )
    {padding -> padding
    }
}