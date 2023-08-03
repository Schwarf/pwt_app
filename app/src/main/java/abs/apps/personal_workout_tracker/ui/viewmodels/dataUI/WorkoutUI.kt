package abs.apps.personal_workout_tracker.ui.viewmodels.dataUI

import abs.apps.personal_workout_tracker.data.database.Workout

data class WorkoutUI(
    val id: Int = 0,
    val name: String = "",
    val sets: String = "",
    val totalRepetitions: String = "",
    val maxRepetitionsInSet: String = "",
    val performances: String=""
)


fun WorkoutUI.toWorkout(): Workout = Workout(
    id = id,
    name = name,
    sets = sets.toIntOrNull() ?: 0,
    totalRepetitions = totalRepetitions.toIntOrNull() ?: 0,
    maxRepetitionsInSet = maxRepetitionsInSet.toIntOrNull() ?: 0,
    performances = performances.toIntOrNull() ?: 0,
)

fun validateWorkoutUI(workoutUI: WorkoutUI): Boolean {
    return with(workoutUI) {
        name.isNotBlank() && sets.isNotBlank() && sets.all { it.isDigit() } &&
                totalRepetitions.isNotBlank() && totalRepetitions.all { it.isDigit() } &&
                maxRepetitionsInSet.isNotBlank() && maxRepetitionsInSet.all { it.isDigit() }

    }
}

data class ValidatedWorkoutUI(
    val workoutUI: WorkoutUI = WorkoutUI(),
    val isValid: Boolean = false
)

