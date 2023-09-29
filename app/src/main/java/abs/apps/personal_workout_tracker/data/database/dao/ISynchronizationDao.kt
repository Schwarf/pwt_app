package abs.apps.personal_workout_tracker.data.database.dao

import abs.apps.personal_workout_tracker.data.database.Synchronization
import abs.apps.personal_workout_tracker.data.database.Training
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface ISynchronizationDao {
    @Upsert
    suspend fun upsertSynchronization(synchronisation: Synchronization)
    @Query("SELECT * FROM synchronization ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatestSynchronizationAttempt()
    @Query("SELECT * FROM synchronization WHERE succeeded = 1 ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatestSuccessfulSynchronization()
}