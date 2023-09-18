package abs.apps.personal_workout_tracker.data.database

import abs.apps.personal_training_tracker.data.database.dao.ITrainingsDao
import abs.apps.personal_workout_tracker.data.database.dao.ITimestampDao
import abs.apps.personal_workout_tracker.data.database.dao.ITrainingTimestampDao
import abs.apps.personal_workout_tracker.data.database.dao.IWorkoutDao
import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [Workout::class, Timestamp::class, Training::class],
    version = 3,
    exportSchema = true,
    autoMigrations = [AutoMigration(from = 1, to = 2)]
)
abstract class TrackerDatabase : RoomDatabase() {
    abstract val workoutDao: IWorkoutDao
    abstract val timestampDao: ITimestampDao
    abstract val trainingDao: ITrainingsDao

    companion object {
        @Volatile
        private var Instance: TrackerDatabase? = null
        val migration2to3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE timestamps RENAME TO workout_timestamps")
            }
        }
        val migration3to4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS training_timestamps (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `trainingId` INTEGER NOT NULL DEFAULT 0, `timestamp` INTEGER NOT NULL, FOREIGN KEY(`trainingId`) REFERENCES `trainings`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
            }
      }

        fun getDatabase(context: Context): TrackerDatabase {

            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, TrackerDatabase::class.java, "workout.db")
                    /**
                     * Setting this option in your app's database builder means that Room
                     * permanently deletes all data from the tables in your database when it
                     * attempts to perform a migration with no defined migration path.
                     */
                    .addMigrations(TrackerDatabase.migration2to3)
//                    .addMigrations(TrackerDatabase.migration2to3, TrackerDatabase.migration3to4)
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}