package abs.apps.personal_workout_tracker.data.repositories

import abs.apps.personal_workout_tracker.data.database.Training
import abs.apps.personal_workout_tracker.data.database.Workout
import kotlinx.coroutines.flow.Flow

interface ITrainingRepository {
    fun getAllTrainingsStream(): Flow<List<Training>>

    fun getTrainingStream(id: Int): Flow<Training?>

    suspend fun upsertTraining(training: Training)

    suspend fun deleteTraining(trainingId: Int)

    suspend fun updateTrainingPerformances(id: Int, performances: Int)
    suspend fun getAllTrainingsForTimestampRange(start: Long, end: Long) : Flow<List<Training>>
    suspend fun getUpdatesForSynchronization(lastSynchronizationTimestamp: Long) : Flow<List<Training>>
}