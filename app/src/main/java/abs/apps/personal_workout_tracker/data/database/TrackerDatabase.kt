package abs.apps.personal_workout_tracker.data.database

import abs.apps.personal_training_tracker.data.database.dao.ITrainingsDao
import abs.apps.personal_workout_tracker.data.database.dao.ITimestampDao
import abs.apps.personal_workout_tracker.data.database.dao.IWorkoutDao
//import abs.apps.personal_workout_tracker.data.database.dao.ITrainingTimestampDao
import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameTable
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [Workout::class, Timestamp::class, Training::class],
    version = 3,
    exportSchema = true,
    autoMigrations = [AutoMigration(from = 1, to = 2), AutoMigration(
        from = 2,
        to = 3,
        spec = TrackerDatabase.Migration2to3::class
    )]
)
abstract class TrackerDatabase : RoomDatabase() {
    abstract val workoutDao: IWorkoutDao
    abstract val timestampDao: ITimestampDao
    abstract val trainingDao: ITrainingsDao

    @RenameTable(fromTableName = "timestamps", toTableName = "workout_timestamps")
    class Migration2to3 : AutoMigrationSpec

    companion object {
        @Volatile
        private var Instance: TrackerDatabase? = null
        fun getDatabase(context: Context): TrackerDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
//            val migration2to3 = object : Migration(2, 3) {
//                override fun migrate(database: SupportSQLiteDatabase) {
//                    database.execSQL("CREATE TABLE IF NOT EXISTS training_timestamps")
//                }
//            }
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, TrackerDatabase::class.java, "workout.db")
                    /**
                     * Setting this option in your app's database builder means that Room
                     * permanently deletes all data from the tables in your database when it
                     * attempts to perform a migration with no defined migration path.
                     */
//                    .addMigrations(migration2to3)
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}