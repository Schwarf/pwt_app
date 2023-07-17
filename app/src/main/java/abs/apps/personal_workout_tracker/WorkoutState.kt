package abs.apps.personal_workout_tracker

data class WorkoutState(
    val workouts: List<Workout> = emptyList(),
    val name: String = "",
    val sets: Int = 0,
    val totalRepetitions: Int=0,
    val maxRepetitionsInSet: Int=0,
    val isAddingWorkout: Boolean = false,
    val isEditingWorkout: Boolean = false
)
