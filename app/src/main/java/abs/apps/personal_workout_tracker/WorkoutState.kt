package abs.apps.personal_workout_tracker

import abs.apps.personal_workout_tracker.data.Workout

data class WorkoutState(
    val workouts: List<Workout> = emptyList(),
    val name: String = "",
    val sets: String = "",
    val totalRepetitions: String = "",
    val maxRepetitionsInSet: String = "",
    val isAddingWorkout: Boolean = false,
    val isChoosingAction: Boolean = false,
    val isEditingWorkout: Boolean = false
)
