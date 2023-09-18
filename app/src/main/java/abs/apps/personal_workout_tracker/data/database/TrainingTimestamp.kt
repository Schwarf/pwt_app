package abs.apps.personal_workout_tracker.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "training_timestamps",
    foreignKeys = [ForeignKey(
        entity = Training::class,
        parentColumns = ["id"],
        childColumns = ["trainingId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["trainingId"])]
)
data class TrainingTimestamp(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "trainingId")
    val trainingId: Int,
    @ColumnInfo(name = "timestamp")
    val timestamp: Long
)

