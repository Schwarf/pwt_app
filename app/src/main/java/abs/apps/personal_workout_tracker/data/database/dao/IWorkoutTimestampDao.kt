package abs.apps.personal_workout_tracker.data.database.dao

import abs.apps.personal_workout_tracker.data.database.Training
import abs.apps.personal_workout_tracker.data.database.WorkoutTimestamp
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface IWorkoutTimestampDao {
    @Upsert
    suspend fun upsertTimestamp(workoutTimestamp: WorkoutTimestamp)

    @Query("UPDATE workout_timestamps SET IsDeleted = 1, lastModified = :lastModified WHERE id = :id")
    suspend fun softDeleteTimestamp(id: Int, lastModified: Long)

    @Query("SELECT * FROM workout_timestamps")
    fun getAllTimestamps(): Flow<List<WorkoutTimestamp>>

    @Query("SELECT * FROM workout_timestamps WHERE workoutId = :workoutId AND isDeleted = 0")
    fun getAllTimestampsForOneWorkout(workoutId: Int): Flow<List<WorkoutTimestamp>>

    @Query("SELECT * FROM workout_timestamps WHERE workoutId = :workoutId AND isDeleted = 0 ORDER BY timestamp DESC LIMIT 1")
    fun getLatestTimestampForOneWorkout(workoutId: Int): Flow<WorkoutTimestamp>

    @Query("SELECT workout_timestamps.* FROM workout_timestamps WHERE workout_timestamps.lastModified > :lastSynchronizationTimestamp")
    fun getUpdatesForSynchronization(lastSynchronizationTimestamp: Long): List<WorkoutTimestamp>

}
