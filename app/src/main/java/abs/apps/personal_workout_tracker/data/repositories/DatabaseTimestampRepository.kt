package abs.apps.personal_workout_tracker.data.repositories

import abs.apps.personal_workout_tracker.data.database.Timestamp
import abs.apps.personal_workout_tracker.data.database.dao.ITimestampDao
import kotlinx.coroutines.flow.Flow

class DatabaseTimestampRepository(private val timestampDao: ITimestampDao) : ITimestampRepository {
    override fun getAllTimestampsStream(): Flow<List<Timestamp>> = timestampDao.getAllTimestamps()

    override fun getTimestampsStreamForOneWorkout(workoutId: Int): Flow<List<Timestamp>> =
        timestampDao.getAllTimestampsForOneWorkout(workoutId)

    override fun getLatestTimestampStreamForOneWorkout(workoutId: Int): Flow<Timestamp> =
        timestampDao.getLatestTimestampForOneWorkout(workoutId)

    override suspend fun upsertTimestamp(timestamp: Timestamp) =
        timestampDao.upsertTimestamp(timestamp)

    override suspend fun deleteTimestamp(timestamp: Timestamp) =
        timestampDao.deleteTimestamp(timestamp)
}