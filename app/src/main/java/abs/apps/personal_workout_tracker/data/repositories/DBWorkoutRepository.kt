package abs.apps.personal_workout_tracker.data.repositories

import abs.apps.personal_workout_tracker.data.database.Training
import abs.apps.personal_workout_tracker.data.database.Workout
import abs.apps.personal_workout_tracker.data.database.dao.IWorkoutDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class DBWorkoutRepository(private val workoutDao: IWorkoutDao) : IWorkoutRepository {
    override fun getAllWorkoutsStream(): Flow<List<Workout>> = workoutDao.getAllWorkouts()

    override fun getWorkoutStream(id: Int): Flow<Workout?> = workoutDao.getWorkout(id)

    override suspend fun upsertWorkout(workout: Workout) = workoutDao.upsertWorkout(workout)

    override suspend fun deleteWorkout(workoutId: Int) =
        workoutDao.softDeleteWorkout(workoutId, lastModified = System.currentTimeMillis())

    override suspend fun updateWorkoutPerformances(id: Int, performances: Int) {
        val workout = workoutDao.getWorkout(id).firstOrNull()
        workout?.let {
            val updatedWorkout = it.copy(performances = performances)
            workoutDao.upsertWorkout(updatedWorkout)
        }
    }

    override suspend fun getAllWorkoutsForTimestampRange(
        start: Long,
        end: Long
    ): Flow<List<Workout>> =
        workoutDao.getWorkoutsByTimestampRange(start, end)

    override suspend fun getUpdatesForSynchronization(lastSynchronizationTimestamp: Long): Flow<List<Workout>> =
        workoutDao.getUpdatesForSynchronization(lastSynchronizationTimestamp)
}