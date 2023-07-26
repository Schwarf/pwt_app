package abs.apps.personal_workout_tracker.data

import kotlinx.coroutines.flow.Flow

interface IWorkoutRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllWorkoutsStream(): Flow<List<Workout>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getWorkoutStream(id: Int): Flow<Workout?>

    /**
     * Insert item in the data source
     */
    suspend fun insertWorkout(workout: Workout)

    /**
     * Delete item from the data source
     */
    suspend fun deleteWorkout(workout: Workout)

    /**
     * Update item in the data source
     */
    suspend fun updateWorkout(workout: Workout)

}