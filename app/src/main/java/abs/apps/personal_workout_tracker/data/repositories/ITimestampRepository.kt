package abs.apps.personal_workout_tracker.data.repositories

import abs.apps.personal_workout_tracker.data.database.Timestamp
import kotlinx.coroutines.flow.Flow

interface ITimestampRepository {
    fun getAllTimestampsStream(): Flow<List<Timestamp>>

    fun getTimestampsStreamForOneWorkout(workoutId: Int): Flow<List<Timestamp>>

    fun getLatestTimestampStreamForOneWorkout(workoutId: Int): Flow<Timestamp>
    suspend fun upsertTimestamp(timestamp: Timestamp)

    suspend fun deleteTimestamp(timestamp: Timestamp)
}