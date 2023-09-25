package abs.apps.personal_workout_tracker.ui.custom_elements

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

enum class MainButtonState {
    EXPANDED,
    COLLAPSED
}

class Item(
    val icon: ImageVector,
    val label: String,
    val onClicked: () -> Unit
)


@Composable
fun ExpandableFloatingActionButton(
    fabIcon: ImageVector,
    items: List<Item>,
    onMainButtonStateChanged: ((state: MainButtonState) -> Unit)? = null
) {
    var currentState by remember { mutableStateOf(MainButtonState.COLLAPSED) }
    val transition: Transition<MainButtonState> =
        updateTransition(targetState = currentState, label = "")
    // State Change
    val stateChange: () -> Unit = {
        currentState = if (transition.currentState == MainButtonState.EXPANDED) {
            MainButtonState.COLLAPSED
        } else MainButtonState.EXPANDED
        onMainButtonStateChanged?.invoke(currentState)
    }
    // Fab Rotation Animation
    val rotation: Float by transition.animateFloat(
        transitionSpec = {
            if (targetState == MainButtonState.EXPANDED) {
                spring(stiffness = Spring.StiffnessLow)
            } else {
                spring(stiffness = Spring.StiffnessMedium)
            }
        },
        label = ""
    ) { state ->
        if (state == MainButtonState.EXPANDED) 45f else 0f
    }

    Column(
        horizontalAlignment = Alignment.End,
    ) {
        items.forEach { item ->
            RowButton(
                item = item,
                transition = transition,
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
        FloatingActionButton(onClick = {
            stateChange()
        }) {
            Icon(
                imageVector = fabIcon,
                contentDescription = "",
                modifier = Modifier.rotate(rotation)
            )
        }
    }
}

@Composable
fun RowButton(
    item: Item,
    transition: Transition<MainButtonState>
) {
    // Mini Fab Alpha Animation
    val alpha: Float by transition.animateFloat(
        transitionSpec = {
            tween(durationMillis = 50)
        }, label = ""
    ) { state ->
        if (state == MainButtonState.EXPANDED) 1f else 0f
    }
    // Mini Fab Scale Animation
    val scale: Float by transition.animateFloat(
        label = ""
    ) { state ->
        if (state == MainButtonState.EXPANDED) 1.0f else 0f
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .alpha(animateFloatAsState((alpha)).value)
            .scale(animateFloatAsState(targetValue = scale).value)
    ) {
        val backgroundColor = MaterialTheme.colorScheme.primaryContainer
        Text(
            text = item.label,
            color = contentColorFor(backgroundColor = backgroundColor),
            modifier = Modifier
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(12.0.dp)
                )
                .padding(start = 6.dp, end = 6.dp, top = 4.dp, bottom = 4.dp)
                .clickable(onClick = { item.onClicked() })
        )
        SmallFloatingActionButton(
            modifier = Modifier
                .padding(4.dp),
            onClick = { item.onClicked() },
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 2.dp,
                hoveredElevation = 4.dp
            )
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.label
            )
        }

    }
}
