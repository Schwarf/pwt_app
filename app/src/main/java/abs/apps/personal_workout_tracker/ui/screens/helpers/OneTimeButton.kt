package abs.apps.personal_workout_tracker.ui.screens.helpers

import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OneTimeButton(onClick: () -> Unit, buttonText: String
, modifier: Modifier, shapes: Shape) {
    var isButtonEnabled by remember { mutableStateOf(true) }

    Button(
        onClick = {
            if (isButtonEnabled) {
                onClick()
                isButtonEnabled = false
            }
        },
        enabled = isButtonEnabled
    ) {
        Text(buttonText)
    }
}

