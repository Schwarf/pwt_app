package abs.apps.personal_workout_tracker.ui.screens

import abs.apps.personal_workout_tracker.R
import abs.apps.personal_workout_tracker.ui.navigation.INavigationDestination
import abs.apps.personal_workout_tracker.ui.screens.helpers.AppTopBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
    {padding ->
        Box(
            contentAlignment = Alignment.Center
        ) {
            // Replace R.drawable.ic_launcher_foreground with your image resource ID
            val image = painterResource(R.drawable.sumocat)
            Image(
                painter = image,
                contentDescription = null, // Provide a description if needed
                modifier = Modifier.padding(padding).fillMaxSize() // Set the size of the image if needed
            )
        }
    }
}