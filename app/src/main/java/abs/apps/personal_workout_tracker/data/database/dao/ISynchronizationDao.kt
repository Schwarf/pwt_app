package abs.apps.personal_workout_tracker.data.database.dao

import abs.apps.personal_workout_tracker.data.database.Synchronization
import abs.apps.personal_workout_tracker.data.database.Training
import androidx.room.Dao
import androidx.room.Upsert

@Dao
interface ISynchronizationDao {
    @Upsert
    suspend fun upsertSynchronization(synchronisation: Synchronization)
}