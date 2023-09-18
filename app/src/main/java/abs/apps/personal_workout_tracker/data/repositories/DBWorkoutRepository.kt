package abs.apps.personal_workout_tracker.data.repositories

import abs.apps.personal_workout_tracker.data.database.dao.IWorkoutDao
import abs.apps.personal_workout_tracker.data.database.Workout
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class DBWorkoutRepository(private val workoutDao: IWorkoutDao) : IWorkoutRepository {
    override fun getAllWorkoutsStream(): Flow<List<Workout>> = workoutDao.getAllWorkouts()

    override fun getWorkoutStream(id: Int): Flow<Workout?> = workoutDao.getWorkout(id)

    override suspend fun upsertWorkout(workout: Workout) = workoutDao.upsertWorkout(workout)

    override suspend fun deleteWorkout(workout: Workout) = workoutDao.deleteWorkout(workout)

    override suspend fun updateWorkoutPerformances(id: Int, performances: Int) {
        val workout = workoutDao.getWorkout(id).firstOrNull()
        workout?.let {
            val updatedWorkout = it.copy(performances = performances)
            workoutDao.upsertWorkout(updatedWorkout)
        }
    }
}