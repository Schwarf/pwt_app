package abs.apps.personal_workout_tracker.ui.viewmodels.dataUI

import abs.apps.personal_workout_tracker.data.database.Training

data class  TrainingUI(
    val id: Int = 0,
    val name: String = "",
    val durationInMinutes: String = "",
    val performances: String = "",
)

fun validateTrainingUI(trainingUI: TrainingUI): Boolean {
    return with(trainingUI) {
        name.isNotBlank() && durationInMinutes.isNotBlank() && durationInMinutes.all { it.isDigit() }
    }
}

fun TrainingUI.toTraining(): Training = Training(
    id = id,
    name = name,
    durationMinutes = durationInMinutes.toIntOrNull() ?: 0,
    performances = performances.toIntOrNull() ?: 0,
    isDeleted = false,
    lastModified = System.currentTimeMillis()
)


data class ValidatedTrainingUI(
    val trainingUI: TrainingUI = TrainingUI(),
    val isValid: Boolean = false
)
