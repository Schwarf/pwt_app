package abs.apps.personal_workout_tracker.data.database

import abs.apps.personal_workout_tracker.ui.viewmodels.dataUI.TrainingUI
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trainings")
data class Training(
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "time_interval_minutes")
    var timeIntervalMinuts: Int,
    @ColumnInfo(name = "performances")
    var performances: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

fun Training.toTrainingUI(): TrainingUI = TrainingUI(
    id = id,
    name = name,
    timeIntervalMinutes = timeIntervalMinuts.toString(),
    performances = performances.toString()
)
