package abs.apps.personal_workout_tracker.data.database.dao

import abs.apps.personal_workout_tracker.data.database.TrainingTimestamp
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ITrainingTimestampDao {
    @Upsert
    suspend fun upsertTimestamp(timestamp: TrainingTimestamp)

    @Delete
    suspend fun deleteTimestamp(timestamp: TrainingTimestamp)

    @Query("SELECT * FROM training_timestamps")
    fun getAllTimestamps(): Flow<List<TrainingTimestamp>>

    @Query("SELECT * FROM training_timestamps WHERE trainingId = :trainingId")
    fun getAllTimestampsForOneTraining(trainingId: Int): Flow<List<TrainingTimestamp>>

    @Query("SELECT * FROM training_timestamps WHERE trainingId = :trainingId ORDER BY timestamp DESC LIMIT 1")
    fun getLatestTimestampForOneTraining(trainingId: Int): Flow<TrainingTimestamp>

}