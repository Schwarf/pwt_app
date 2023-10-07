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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay

object StartupDestination : INavigationDestination {
    override val route = "startup"
    override val titleRes = R.string.startup
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SynchronizationScreen(navigateToStartScreen: () -> Unit,
                         viewModel: SynchronizationViewModel = viewModel(factory = AppViewModelProvider.Factory)
)
{
}