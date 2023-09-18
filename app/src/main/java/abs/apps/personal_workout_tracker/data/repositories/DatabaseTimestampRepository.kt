package abs.apps.personal_workout_tracker.data.repositories

import abs.apps.personal_workout_tracker.data.database.WorkoutTimestamp
import abs.apps.personal_workout_tracker.data.database.dao.ITimestampDao
import kotlinx.coroutines.flow.Flow

class DatabaseTimestampRepository(private val timestampDao: ITimestampDao) : ITimestampRepository {
    override fun getAllTimestampsStream(): Flow<List<WorkoutTimestamp>> = timestampDao.getAllTimestamps()

    override fun getTimestampsStreamForOneWorkout(workoutId: Int): Flow<List<WorkoutTimestamp>> =
        timestampDao.getAllTimestampsForOneWorkout(workoutId)

    override fun getLatestTimestampStreamForOneWorkout(workoutId: Int): Flow<WorkoutTimestamp> =
        timestampDao.getLatestTimestampForOneWorkout(workoutId)

    override suspend fun upsertTimestamp(workoutTimestamp: WorkoutTimestamp) =
        timestampDao.upsertTimestamp(workoutTimestamp)

    override suspend fun deleteTimestamp(workoutTimestamp: WorkoutTimestamp) =
        timestampDao.deleteTimestamp(workoutTimestamp)
}