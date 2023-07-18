package abs.apps.personal_workout_tracker

sealed interface WorkoutEvent
{
    object SaveWorkout: WorkoutEvent //  defines a singleton
    data class SetName(val name: String): WorkoutEvent
    data class SetSets(val sets: String): WorkoutEvent
    data class SetTotalRepetitions(val totalRepetitions: String): WorkoutEvent
    data class SetMaxRepetitionsInSet(val maxRepetitionsInSet: String): WorkoutEvent
    object ShowDialog: WorkoutEvent
    object HideDialog: WorkoutEvent
    data class DeleteWorkout(val workout: Workout): WorkoutEvent
}