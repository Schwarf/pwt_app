package abs.apps.personal_workout_tracker.ui.screens

import abs.apps.personal_workout_tracker.R
import abs.apps.personal_workout_tracker.ui.AppViewModelProvider
import abs.apps.personal_workout_tracker.ui.navigation.INavigationDestination
import abs.apps.personal_workout_tracker.ui.screens.helpers.AppTopBar
import abs.apps.personal_workout_tracker.ui.viewmodels.synchronization.SynchronizationViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel

object SynchronizationDestination : INavigationDestination {
    override val route = "synchronization"
    override val titleRes = R.string.synchronization
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SynchronizationScreen(viewModel: SynchronizationViewModel= viewModel(factory = AppViewModelProvider.Factory)) {


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
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize() // Set the size of the image if needed
            )
        }
    }

}
