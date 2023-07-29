package abs.apps.personal_workout_tracker.data.database

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
    tableName = "timestamps",
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


fun Long.toLocalDateTimeString(): String {
    return LocalDateTime.ofInstant(Instant.ofEpochSecond(this), ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("yyyy-HH-dd HH:mm::ss"))
}