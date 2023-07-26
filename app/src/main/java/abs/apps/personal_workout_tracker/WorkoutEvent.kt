package abs.apps.personal_workout_tracker

import abs.apps.personal_workout_tracker.data.Workout

sealed interface WorkoutEvent {
    object SaveWorkout : WorkoutEvent //  defines a singleton
    data class SetName(val name: String) : WorkoutEvent
    data class SetSets(val sets: String) : WorkoutEvent
    data class SetTotalRepetitions(val totalRepetitions: String) : WorkoutEvent
    data class SetMaxRepetitionsInSet(val maxRepetitionsInSet: String) : WorkoutEvent
    data class DeleteWorkout(val workout: Workout) : WorkoutEvent
    object ShowAddDialog : WorkoutEvent
    object HideAddDialog : WorkoutEvent
    object ShowChooseActionDialog : WorkoutEvent
    object HideChooseActionDialog : WorkoutEvent
    object ShowEditDialog : WorkoutEvent
    object HideEditDialog : WorkoutEvent

}