package abs.apps.personal_workout_tracker.ui.screens.helpers

import abs.apps.personal_workout_tracker.R
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

@Composable
private fun ChooseAddTrainingOrAddWorkoutDialog(
    onAddWorkout: () -> Unit, onAddTraining: () -> Unit,
    modifier: Modifier = Modifier,

    ) {
    val bulletPoint = "\u2022"
    val content = listOf(
        stringResource(R.string.workout_explanation) , stringResource(R.string.training_explanation)
    )
    val paragraphStyle = ParagraphStyle(textIndent = TextIndent(restLine = 12.sp))
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        title = { Text(stringResource(R.string.choose_add)) },
        text = { Text(buildAnnotatedString {
            content.forEach{withStyle(style = paragraphStyle){
                append(bulletPoint)
                append("\t\t")
                append(it)
            } }
        }) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onAddTraining) {
                Text(text = stringResource(R.string.add_workout))
            }
        },
        confirmButton = {
            TextButton(onClick = onAddWorkout) {
                Text(text = stringResource(R.string.add_training))
            }
        })
}
