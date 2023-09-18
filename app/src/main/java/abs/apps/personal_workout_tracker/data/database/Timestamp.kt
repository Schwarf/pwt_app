package abs.apps.personal_workout_tracker.data.database

import abs.apps.personal_workout_tracker.ui.viewmodels.dataUI.TimestampUI
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
    tableName = "workout_timestamps",
    foreignKeys = [ForeignKey(
        entity = Workout::class,
        parentColumns = ["id"],
        childColumns = ["workoutId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["workoutId"])]
)

data class Timestamp(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "workoutId")
    val workoutId: Int,
    @ColumnInfo(name = "timestamp")
    val timestamp: Long,
)

fun Timestamp.toTimestampUI(): TimestampUI = TimestampUI(
    id = id,
    workoutId = workoutId,
    dateTimeString = LocalDateTime.ofInstant(
        Instant.ofEpochSecond(timestamp), ZoneId.systemDefault()
    ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm::ss"))
)


