package abs.apps.personal_workout_tracker.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Workout::class],
    version = 1,
    exportSchema = false
)
abstract class WorkoutDatabase : RoomDatabase() {
    abstract val dao: IWorkoutDao
}