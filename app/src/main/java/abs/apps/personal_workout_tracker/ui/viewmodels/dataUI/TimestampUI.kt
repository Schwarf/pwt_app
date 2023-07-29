package abs.apps.personal_workout_tracker.ui.viewmodels.dataUI

import abs.apps.personal_workout_tracker.data.database.Performance
import abs.apps.personal_workout_tracker.data.database.Timestamp
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

data class TimestampUI(
    val id: Int = 0,
    val workoutId: Int = 0,
    val dateTimeString: String = "",
    val isDateTimeValid: Boolean = false
)

fun TimestampUI.toTimestamp(): Timestamp = Timestamp(
    id = id,
    workoutId = workoutId,
    timestamp = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond()
)