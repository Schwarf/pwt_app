package abs.apps.personal_workout_tracker.data

import kotlinx.coroutines.flow.Flow

interface IPerformanceRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllPerformancesStream(): Flow<List<Performance>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getPerformancesStreamForOneWorkout(workoutId: Int): Flow<Performance>

    /**
     * Insert item in the data source
     */
    suspend fun insertPerformance(performance: Performance)

    /**
     * Delete item from the data source
     */
    suspend fun deletePerformance(performance: Performance)
}