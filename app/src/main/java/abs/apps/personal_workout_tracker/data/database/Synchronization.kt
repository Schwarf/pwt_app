package abs.apps.personal_workout_tracker.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "synchronization")
data class Synchronization(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "timestamp")
    val timestamp: Long,
    @ColumnInfo(name = "attempted")
    var attempted: Boolean,
    @ColumnInfo(name = "succeeded")
    var succeeded: Boolean = false,
    @ColumnInfo(name = "sourceUUID")
    val sourceUUID: String = UUID.randomUUID().toString(),
)
