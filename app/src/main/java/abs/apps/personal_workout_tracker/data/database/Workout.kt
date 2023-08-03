package abs.apps.personal_workout_tracker.data.database

import abs.apps.personal_workout_tracker.ui.viewmodels.dataUI.ValidatedWorkoutUI
import abs.apps.personal_workout_tracker.ui.viewmodels.dataUI.WorkoutUI
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
    @ColumnInfo(name = "performances")
    var performances: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

fun Workout.toValidatedWorkoutUI(isValid: Boolean = false): ValidatedWorkoutUI = ValidatedWorkoutUI(
    workoutUI = this.toWorkoutUI(),
    isValid = isValid
)

fun Workout.toWorkoutUI(): WorkoutUI = WorkoutUI(
    id = id,
    name = name,
    sets = sets.toString(),
    totalRepetitions = totalRepetitions.toString(),
    maxRepetitionsInSet = maxRepetitionsInSet.toString(),
    performances = performances.toString()
)
