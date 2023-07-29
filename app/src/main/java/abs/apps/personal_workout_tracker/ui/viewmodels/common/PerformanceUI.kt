package abs.apps.personal_workout_tracker.ui.viewmodels.common

import abs.apps.personal_workout_tracker.data.database.Performance


data class PerformanceUI
    (
    val id: Int = 0,
    val workoutId: Int = 0,
    val performedCounter: String = "0",
    val isPerformanceValid: Boolean = false
)

fun PerformanceUI.toPerformance(): Performance = Performance(
    id = id,
    workoutId = workoutId,
    performedCounter = performedCounter.toIntOrNull() ?: 0
)