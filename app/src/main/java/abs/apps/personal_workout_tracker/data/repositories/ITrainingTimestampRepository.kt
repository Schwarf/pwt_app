package abs.apps.personal_training_tracker.data.repositories

import abs.apps.personal_workout_tracker.data.database.Training
import abs.apps.personal_workout_tracker.data.database.TrainingTimestamp
import kotlinx.coroutines.flow.Flow

interface ITrainingTimestampRepository {
    fun getAllTimestampsStream(): Flow<List<TrainingTimestamp>>

    fun getTimestampsStreamForOneTraining(trainingId: Int): Flow<List<TrainingTimestamp>>

    fun getLatestTimestampStreamForOneTraining(trainingId: Int): Flow<TrainingTimestamp>
    suspend fun upsertTimestamp(trainingTimestamp: TrainingTimestamp)
    suspend fun deleteTimestamp(id: Int)
    suspend fun getUpdatesForSynchronization(lastSynchronizationTimestamp: Long) : Flow<List<Training>>

}