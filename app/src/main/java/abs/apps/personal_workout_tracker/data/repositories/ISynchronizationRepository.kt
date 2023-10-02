package abs.apps.personal_workout_tracker.data.repositories

import abs.apps.personal_workout_tracker.data.database.Synchronization

interface ISynchronizationRepository {
    suspend fun upsertSynchronization(synchronisation: Synchronization)
    suspend fun getLatestSynchronizationAttempt() : Long
    suspend fun getLatestSuccessfulSynchronization() : Long
}