package abs.apps.personal_workout_tracker.data

import kotlinx.coroutines.flow.Flow

class DatabaseWorkoutRepository(private val workoutDao: IWorkoutDao) : IWorkoutRepository {
    override fun getAllWorkoutsStream(): Flow<List<Workout>> = workoutDao.getAllWorkouts()

    override fun getWorkoutStream(id: Int): Flow<Workout?> = workoutDao.getWorkout(id)

    override suspend fun upsertWorkout(workout: Workout) = workoutDao.upsertWorkout(workout)

    override suspend fun deleteWorkout(workout: Workout) = workoutDao.deleteWorkout(workout)

}