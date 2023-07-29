package abs.apps.personal_workout_tracker.ui.viewmodels.dataUI

import abs.apps.personal_workout_tracker.data.database.Workout

data class WorkoutUI(
    val id: Int = 0,
    val name: String = "",
    val sets: String = "",
    val totalRepetitions: String = "",
    val maxRepetitionsInSet: String = ""
)


fun WorkoutUI.toWorkout(): Workout = Workout(
    id = id,
    name = name,
    sets = sets.toIntOrNull() ?: 0,
    totalRepetitions = totalRepetitions.toIntOrNull() ?: 0,
    maxRepetitionsInSet = maxRepetitionsInSet.toIntOrNull() ?: 0
)
