package abs.apps.personal_workout_tracker.data.database

import abs.apps.personal_workout_tracker.ui.viewmodels.dataUI.WorkoutTimestampUI
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

data class WorkoutTimestamp(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "workoutId")
    val workoutId: Int,
    @ColumnInfo(name = "timestamp")
    val timestamp: Long,
    var lastModified: Long,
    @ColumnInfo(name = "isDeleted")
    var isDeleted: Boolean = false
)

fun WorkoutTimestamp.toWorkoutTimestampUI(): WorkoutTimestampUI = WorkoutTimestampUI(
    id = id,
    workoutId = workoutId,
    dateTimeString = LocalDateTime.ofInstant(
        Instant.ofEpochSecond(timestamp), ZoneId.systemDefault()
    ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm::ss"))
)


