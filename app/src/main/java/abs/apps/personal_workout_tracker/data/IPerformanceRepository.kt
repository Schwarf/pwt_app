package abs.apps.personal_workout_tracker.data

import kotlinx.coroutines.flow.Flow

interface IPerformanceRepository {
    /**
     * Retrieve all the workouts from the the given data source.
     */
    fun getAllPerformancesStream(): Flow<List<Performance>>

    /**
     * Retrieve an workout from the given data source that matches with the [id].
     */
    fun getPerformancesStreamForOneWorkout(workoutId: Int): Flow<Performance>

    /**
     * Update performance in the data source
     */
    suspend fun upsertPerformance(performance: Performance)

    /**
     * Delete workout from the data source
     */
    suspend fun deletePerformance(performance: Performance)
}