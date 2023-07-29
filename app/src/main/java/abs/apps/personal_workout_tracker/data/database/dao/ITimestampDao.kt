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
    suspend fun upsertTimestamp(Timestamp: Timestamp)

    @Delete
    suspend fun deleteTimestamp(Timestamp: Timestamp)

    @Query("SELECT * FROM timestamps")
    fun getAllTimestamps(): Flow<List<Timestamp>>

    @Query("SELECT * FROM timestamps WHERE workoutId = :workoutId")
    fun getAllTimestampsForOneWorkout(workoutId: Int): Flow<List<Timestamp>>
}
