package abs.apps.personal_workout_tracker.data.database.dao

import abs.apps.personal_workout_tracker.data.database.Timestamp
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ITimestampDao {
    @Upsert
    suspend fun upsertTimestamp(timestamp: Timestamp)

    @Delete
    suspend fun deleteTimestamp(timestamp: Timestamp)

    @Query("SELECT * FROM workout_timestamps")
    fun getAllTimestamps(): Flow<List<Timestamp>>

    @Query("SELECT * FROM workout_timestamps WHERE workoutId = :workoutId")
    fun getAllTimestampsForOneWorkout(workoutId: Int): Flow<List<Timestamp>>

    @Query("SELECT * FROM workout_timestamps WHERE workoutId = :workoutId ORDER BY timestamp DESC LIMIT 1")
    fun getLatestTimestampForOneWorkout(workoutId: Int): Flow<Timestamp>
}
