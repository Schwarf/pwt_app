package abs.apps.personal_workout_tracker.data.database

import abs.apps.personal_workout_tracker.data.database.dao.ITimestampDao
import abs.apps.personal_workout_tracker.data.database.dao.IWorkoutDao
import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Workout::class, Timestamp::class, Training::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [AutoMigration(from = 1, to = 2)]
)
abstract class TrackerDatabase : RoomDatabase() {
    abstract val workoutDao: IWorkoutDao
    abstract val timestampDao: ITimestampDao

    companion object {
        @Volatile
        private var Instance: TrackerDatabase? = null
        fun getDatabase(context: Context): TrackerDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, TrackerDatabase::class.java, "workout.db")
                    /**
                     * Setting this option in your app's database builder means that Room
                     * permanently deletes all data from the tables in your database when it
                     * attempts to perform a migration with no defined migration path.
                     */
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}