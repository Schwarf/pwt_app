package abs.apps.personal_workout_tracker.data

import abs.apps.personal_workout_tracker.ui.viewmodels.common.PerformanceUI
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "performances",
    foreignKeys = [ForeignKey(
        entity = Workout::class,
        parentColumns = ["id"],
        childColumns = ["workoutId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["workoutId"])]
)
data class Performance(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "workoutId")
    val workoutId: Int,
    @ColumnInfo(name = "performedCounter")
    val performedCounter: Int,
)


fun Performance.toPerformanceUI(): PerformanceUI = PerformanceUI(
    id = id,
    workoutId = workoutId,
    performedCounter = performedCounter.toString(),
    isPerformanceValid = true
)
