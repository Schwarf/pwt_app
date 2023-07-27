package abs.apps.personal_workout_tracker.data

import kotlinx.coroutines.flow.Flow

class DatabasePerformanceRepository (private val performanceDao: IPerformancesDao) : IPerformanceRepository {
    override fun getAllPerformancesStream(): Flow<List<Performance>> = performanceDao.getAllPerformances()

    override fun getPerformancesStreamForOneWorkout(workoutId: Int): Flow<Performance> = performanceDao.getAllPerformancesForOneWorkout(workoutId)

    override suspend fun insertPerformance(performance: Performance) = performanceDao.upsertPerformance(performance)

    override suspend fun deletePerformance(performance: Performance) = performanceDao.deletePerformance(performance)
}