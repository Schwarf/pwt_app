package abs.apps.personal_workout_tracker.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class Workout(
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "sets")
    var sets: Int,
    @ColumnInfo(name = "totalRepetitions")
    var totalRepetitions: Int,
    @ColumnInfo(name = "maxRepetitionsInSet")
    var maxRepetitionsInSet: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
