package abs.apps.personal_workout_tracker.data.repositories

import abs.apps.personal_workout_tracker.data.database.IPerformancesDao
import abs.apps.personal_workout_tracker.data.database.Performance
import kotlinx.coroutines.flow.Flow

class DatabasePerformanceRepository(private val performanceDao: IPerformancesDao) :
    IPerformanceRepository {
    override fun getAllPerformancesStream(): Flow<List<Performance>> =
        performanceDao.getAllPerformances()

    override fun getPerformancesStreamForOneWorkout(workoutId: Int): Flow<Performance> =
        performanceDao.getAllPerformancesForOneWorkout(workoutId)


    override suspend fun upsertPerformance(performance: Performance) =
        performanceDao.upsertPerformance(performance)

    override suspend fun deletePerformance(performance: Performance) =
        performanceDao.deletePerformance(performance)
}