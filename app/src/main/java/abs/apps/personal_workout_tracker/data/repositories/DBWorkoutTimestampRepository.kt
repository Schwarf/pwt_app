package abs.apps.personal_workout_tracker.data.repositories

import abs.apps.personal_workout_tracker.data.database.Training
import abs.apps.personal_workout_tracker.data.database.WorkoutTimestamp
import abs.apps.personal_workout_tracker.data.database.dao.IWorkoutTimestampDao
import kotlinx.coroutines.flow.Flow

class DBWorkoutTimestampRepository(private val workoutTimestampDao: IWorkoutTimestampDao) :
    IWorkoutTimestampRepository {
    override fun getAllTimestampsStream(): Flow<List<WorkoutTimestamp>> =
        workoutTimestampDao.getAllTimestamps()

    override fun getTimestampsStreamForOneWorkout(workoutId: Int): Flow<List<WorkoutTimestamp>> =
        workoutTimestampDao.getAllTimestampsForOneWorkout(workoutId)

    override fun getLatestTimestampStreamForOneWorkout(workoutId: Int): Flow<WorkoutTimestamp> =
        workoutTimestampDao.getLatestTimestampForOneWorkout(workoutId)

    override suspend fun upsertTimestamp(workoutTimestamp: WorkoutTimestamp) =
        workoutTimestampDao.upsertTimestamp(workoutTimestamp)

    override suspend fun deleteTimestamp(id: Int) =
        workoutTimestampDao.softDeleteTimestamp(id, lastModified = System.currentTimeMillis())

    override suspend fun getUpdatesForSynchronization(lastSynchronizationTimestamp: Long): Flow<List<WorkoutTimestamp>> =
        workoutTimestampDao.getUpdatesForSynchronization(lastSynchronizationTimestamp)

}