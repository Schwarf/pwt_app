package abs.apps.personal_workout_tracker.data.database

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class Synchronization(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "timestamp")
    val timestamp: Long,
    @ColumnInfo(name = "attempted")
    var attempted: Boolean,
    @ColumnInfo(name = "succeeded")
    var succeeded: Boolean = false
)
