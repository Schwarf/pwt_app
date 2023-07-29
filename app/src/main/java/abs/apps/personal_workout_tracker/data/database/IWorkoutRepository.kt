package abs.apps.personal_workout_tracker.data.database

import kotlinx.coroutines.flow.Flow

interface IWorkoutRepository {
    fun getAllWorkoutsStream(): Flow<List<Workout>>

    fun getWorkoutStream(id: Int): Flow<Workout?>

    suspend fun upsertWorkout(workout: Workout)

    suspend fun deleteWorkout(workout: Workout)
}