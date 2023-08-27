package abs.apps.personal_workout_tracker.ui.screens.helpers

import abs.apps.personal_workout_tracker.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreenTopBar(
    switchToWorkoutsButtonEnabled: Boolean,
    navigateToOtherList: () -> Unit,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    var workoutText = ""
    var trainingText = ""
    val switchSize = 16.sp
    val showSize = 20.sp
    if (switchToWorkoutsButtonEnabled) {
        trainingText = "Switch to trainings"
        workoutText = "Showing all workouts"
    } else {
        trainingText = "Showing all trainings "
        workoutText = "Switch to workouts"

    }
    CenterAlignedTopAppBar(
        title = { },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                TextButton(
                    onClick = navigateToOtherList,
                    enabled = !switchToWorkoutsButtonEnabled
                ) {
                    Text(
                        text = workoutText,
                        style = TextStyle(
                            fontSize = if (!switchToWorkoutsButtonEnabled) switchSize else showSize,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                TextButton(
                    onClick = navigateToOtherList,
                    enabled = switchToWorkoutsButtonEnabled
                ) {
                    Text(
                        text = trainingText,
                        style = TextStyle(
                            fontSize = if (switchToWorkoutsButtonEnabled) switchSize else showSize,
                            fontWeight = FontWeight.Bold
                        )
                    )

                }
            }

        }

    )

}