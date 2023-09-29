package abs.apps.personal_workout_tracker.data.database

import abs.apps.personal_workout_tracker.ui.viewmodels.dataUI.TrainingTimestampUI
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

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
    val timestamp: Long,
    @ColumnInfo(name = "lastModified")
    var lastModified: Long,
    @ColumnInfo(name = "isDeleted")
    var isDeleted: Boolean = false
)

fun TrainingTimestamp.toTrainingTimestampUI(): TrainingTimestampUI = TrainingTimestampUI(
    id = id,
    trainingId = trainingId,
    dateTimeString = LocalDateTime.ofInstant(
        Instant.ofEpochSecond(timestamp), ZoneId.systemDefault()
    ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm::ss"))
)

