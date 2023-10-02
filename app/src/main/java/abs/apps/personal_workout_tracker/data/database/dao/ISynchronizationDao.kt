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
    @Query("SELECT COALESCE(MAX(timestamp), 0) AS latest_timestamp FROM synchronization")
    suspend fun getLatestSynchronizationAttempt()
    @Query("SELECT COALESCE((SELECT MAX(timestamp) FROM synchronization WHERE succeeded = 1), 0) AS latest_timestamp")
    suspend fun getLatestSuccessfulSynchronization()
}