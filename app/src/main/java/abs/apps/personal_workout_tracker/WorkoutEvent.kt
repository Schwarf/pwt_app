package abs.apps.personal_workout_tracker

sealed interface WorkoutEvent
{
    object SaveWorkout: WorkoutEvent //  defines a singleton
    data class SetName(val name: String): WorkoutEvent
    data class SetSets(val sets: Int): WorkoutEvent
    data class SetTotalRepetitions(val totalRepetitions: Int): WorkoutEvent
    data class SetMaxRepetitionsInSet(val maxRepetitionsInSet: Int): WorkoutEvent
    object ShowDialog: WorkoutEvent
    object HideDialog: WorkoutEvent
    data class DeleteWorkout(val workout: Workout): WorkoutEvent
}