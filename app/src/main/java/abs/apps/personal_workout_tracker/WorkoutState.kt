package abs.apps.personal_workout_tracker

data class WorkoutState(
    val workouts: List<Workout> = emptyList(),
    val name: String = "",
    val sets: String = "",
    val totalRepetitions: String = "",
    val maxRepetitionsInSet: String = "",
    val isAddingWorkout: Boolean = false,
    val isEditingWorkout: Boolean = false
)
