package abs.apps.personal_workout_tracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Workout(
    var name: String,
    var sets: Int,
    var totalRepetitions: Int,
    var maxRepetitionsInSet: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
