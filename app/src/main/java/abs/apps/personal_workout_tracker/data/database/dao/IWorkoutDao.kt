package abs.apps.personal_workout_tracker.data.database.dao

import abs.apps.personal_workout_tracker.data.database.Workout
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface IWorkoutDao {
    @Upsert
    suspend fun upsertWorkout(workout: Workout)

    @Query("UPDATE workouts SET IsDeleted = 1, lastModified = :modifiedTimestamp WHERE id = :workoutId")
    suspend fun softDeleteWorkout(workoutId: Int, modifiedTimestamp: Long)

    @Query("SELECT * FROM workouts ORDER BY name ASC")
    fun getAllWorkouts(): Flow<List<Workout>>

    @Query("SELECT * from workouts WHERE id = :id")
    fun getWorkout(id: Int): Flow<Workout>

    @Query(
        "SELECT workouts.* FROM workouts" +
                " INNER JOIN workout_timestamps ON workouts.id = workout_timestamps.workoutId " +
                " WHERE workout_timestamps.timestamp >= :start AND workout_timestamps.timestamp < :end"
    )
    fun getWorkoutsByTimestampRange(start: Long, end: Long): Flow<List<Workout>>
}