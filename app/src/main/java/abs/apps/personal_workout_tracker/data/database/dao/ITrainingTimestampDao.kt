package abs.apps.personal_workout_tracker.data.database.dao

import abs.apps.personal_workout_tracker.data.database.Training
import abs.apps.personal_workout_tracker.data.database.TrainingTimestamp
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ITrainingTimestampDao {
    @Upsert
    suspend fun upsertTimestamp(timestamp: TrainingTimestamp)

    @Query("UPDATE training_timestamps SET IsDeleted = 1, lastModified = :lastModified WHERE id = :id")
    suspend fun softDeleteTimestamp(id: Int, lastModified: Long)

    @Query("SELECT * FROM training_timestamps")
    fun getAllTimestamps(): Flow<List<TrainingTimestamp>>

    @Query("SELECT * FROM training_timestamps WHERE trainingId = :trainingId AND isDeleted = 0")
    fun getAllTimestampsForOneTraining(trainingId: Int): Flow<List<TrainingTimestamp>>

    @Query("SELECT * FROM training_timestamps WHERE trainingId = :trainingId AND isDeleted = 0 ORDER BY timestamp DESC LIMIT 1")
    fun getLatestTimestampForOneTraining(trainingId: Int): Flow<TrainingTimestamp>

    @Query("SELECT training_timestamps.* FROM training_timestamps WHERE training_timestamps.lastModified > :lastSynchronizationTimestamp")
    fun getUpdatesForSynchronization(lastSynchronizationTimestamp: Long): Flow<List<Training>>
}