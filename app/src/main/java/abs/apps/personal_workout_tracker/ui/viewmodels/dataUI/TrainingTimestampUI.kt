package abs.apps.personal_workout_tracker.ui.viewmodels.dataUI

import abs.apps.personal_workout_tracker.data.database.TrainingTimestamp
import abs.apps.personal_workout_tracker.data.database.WorkoutTimestamp
import java.time.LocalDateTime
import java.time.ZoneId

data class TrainingTimestampUI(
    val id: Int = 0,
    val trainingId: Int = 0,
    val dateTimeString: String = "",
    val isDateTimeValid: Boolean = false
)

fun TrainingTimestampUI.TrainingTimestamp(): TrainingTimestamp = TrainingTimestamp(
    id = id,
    trainingId = trainingId,
    timestamp = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond(),
    isDeleted = false,
    lastModified = System.currentTimeMillis()
)