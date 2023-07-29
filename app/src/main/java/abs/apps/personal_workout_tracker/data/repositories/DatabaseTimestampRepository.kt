package abs.apps.personal_workout_tracker.data.repositories

import abs.apps.personal_workout_tracker.data.database.ITimestampsDao
import abs.apps.personal_workout_tracker.data.database.Timestamp
import kotlinx.coroutines.flow.Flow

class DatabaseTimestampRepository(private val timestampDao: ITimestampsDao) : ITimestampRepository {
    override fun getAllTimestampsStream(): Flow<List<Timestamp>> = timestampDao.getAllTimestamps()

    override fun getTimestampsStreamForOneWorkout(workoutId: Int): Flow<List<Timestamp>> =
        timestampDao.getAllTimestampsForOneWorkout(workoutId)

    override suspend fun upsertTimestamp(timestamp: Timestamp) =
        timestampDao.upsertTimestamp(timestamp)

    override suspend fun deleteTimestamp(timestamp: Timestamp) =
        timestampDao.deleteTimestamp(timestamp)
}