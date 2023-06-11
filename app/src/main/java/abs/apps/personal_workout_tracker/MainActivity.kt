package abs.apps.personal_workout_tracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import abs.apps.personal_workout_tracker.ui.theme.Personal_workout_trackerTheme
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.ui.node.modifierElementOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Personal_workout_trackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    AddWorkoutButton()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun AddWorkoutButton(){
    Button(onClick = {}, modifier = Modifier.width(IntrinsicSize.Min).height(IntrinsicSize.Min) ){
        Text(text = "Add workout")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Personal_workout_trackerTheme {
        Greeting("Android")
    }
}