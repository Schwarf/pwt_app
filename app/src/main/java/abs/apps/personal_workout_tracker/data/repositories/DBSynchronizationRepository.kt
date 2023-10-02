package abs.apps.personal_workout_tracker.data.repositories

import abs.apps.personal_workout_tracker.data.database.Synchronization
import abs.apps.personal_workout_tracker.data.database.dao.ISynchronizationDao

class DBSynchronizationRepository(private val synchronizationDao: ISynchronizationDao) :
    ISynchronizationRepository {
    override suspend fun getLatestSynchronizationAttempt(): Long =
        synchronizationDao.getLatestSynchronizationAttempt()

    override suspend fun upsertSynchronization(synchronisation: Synchronization) =
        synchronizationDao.upsertSynchronization(synchronisation)

    override suspend fun getLatestSuccessfulSynchronization(): Long =
        synchronizationDao.getLatestSuccessfulSynchronization()
}