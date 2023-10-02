package abs.apps.personal_workout_tracker.data.database.dao

import abs.apps.personal_workout_tracker.data.database.Training
import abs.apps.personal_workout_tracker.data.database.Workout
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface IWorkoutDao {
    @Upsert
    suspend fun upsertWorkout(workout: Workout)

    @Query("UPDATE workouts SET IsDeleted = 1, lastModified = :lastModified WHERE id = :workoutId")
    suspend fun softDeleteWorkout(workoutId: Int, lastModified: Long)

    @Query("SELECT * FROM workouts WHERE isDeleted = 0 ORDER BY name ASC")
    fun getAllWorkouts(): Flow<List<Workout>>

    @Query("SELECT * from workouts WHERE id = :id AND isDeleted = 0")
    fun getWorkout(id: Int): Flow<Workout>

    @Query(
        "SELECT workouts.* FROM workouts" +
                " INNER JOIN workout_timestamps ON workouts.id = workout_timestamps.workoutId " +
                " WHERE workout_timestamps.timestamp >= :start AND workout_timestamps.timestamp < :end " +
                "AND workout_timestamps.isDeleted = 0"
    )
    fun getWorkoutsByTimestampRange(start: Long, end: Long): Flow<List<Workout>>

    @Query("SELECT workouts.* FROM workouts WHERE workouts.lastModified > :lastSynchronizationTimestamp")
    fun getUpdatesForSynchronization(lastSynchronizationTimestamp: Long): Flow<List<Training>>
}