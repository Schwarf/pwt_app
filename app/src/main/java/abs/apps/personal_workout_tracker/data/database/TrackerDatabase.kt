package abs.apps.personal_workout_tracker.data.database

import abs.apps.personal_workout_tracker.data.repositories.IPerformancesDao
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Workout::class, Performance::class, Timestamps::class],
    version = 1,
    exportSchema = false
)
abstract class TrackerDatabase : RoomDatabase() {
    abstract val workoutDao: IWorkoutDao
    abstract val performanceDao: IPerformancesDao

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