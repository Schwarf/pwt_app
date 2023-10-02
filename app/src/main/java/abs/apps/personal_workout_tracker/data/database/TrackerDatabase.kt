package abs.apps.personal_workout_tracker.data.database

import abs.apps.personal_training_tracker.data.database.dao.ITrainingsDao
import abs.apps.personal_workout_tracker.data.database.dao.ISynchronizationDao
import abs.apps.personal_workout_tracker.data.database.dao.IWorkoutTimestampDao
import abs.apps.personal_workout_tracker.data.database.dao.ITrainingTimestampDao
import abs.apps.personal_workout_tracker.data.database.dao.IWorkoutDao
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

@Database(
    entities = [Workout::class, WorkoutTimestamp::class, Training::class, TrainingTimestamp::class, Synchronization::class],
    version = 1,
    exportSchema = true,
)
abstract class TrackerDatabase : RoomDatabase() {
    abstract val workoutDao: IWorkoutDao
    abstract val timestampDao: IWorkoutTimestampDao
    abstract val trainingDao: ITrainingsDao
    abstract val trainingTimestampDao: ITrainingTimestampDao
    abstract val synchronizationDao: ISynchronizationDao

    companion object {
        @Volatile
        private var Instance: TrackerDatabase? = null

        fun getDatabase(context: Context): TrackerDatabase {

            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, TrackerDatabase::class.java, "workout.db")
                    /**
                     * Setting this option in your app's database builder means that Room
                     * permanently deletes all data from the tables in your database when it
                     * attempts to perform a migration with no defined migration path.
                     */
                    .build()
                    .also { Instance = it }
            }
        }
    }
}