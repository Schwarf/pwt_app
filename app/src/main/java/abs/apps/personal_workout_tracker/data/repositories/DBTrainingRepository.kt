package abs.apps.personal_training_tracker.data.repositories

import abs.apps.personal_training_tracker.data.database.dao.ITrainingsDao
import abs.apps.personal_workout_tracker.data.database.Training
import abs.apps.personal_workout_tracker.data.repositories.ITrainingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class DBTrainingRepository(private val trainingDao: ITrainingsDao) : ITrainingRepository {
    override fun getAllTrainingsStream(): Flow<List<Training>> = trainingDao.getAllTrainings()

    override fun getTrainingStream(id: Int): Flow<Training?> = trainingDao.getTraining(id)

    override suspend fun upsertTraining(training: Training) = trainingDao.upsertTraining(training)

    override suspend fun deleteTraining(training: Training) = trainingDao.deleteTraining(training)

    override suspend fun updateTrainingPerformances(id: Int, performances: Int) {
        val training = trainingDao.getTraining(id).firstOrNull()
        training?.let {
            val updatedTraining = it.copy(performances = performances)
            trainingDao.upsertTraining(updatedTraining)
        }
    }

    override suspend fun getAllTrainingsForTimestampRange(start: Long, end: Long) =
        trainingDao.getTrainingsByTimestampRange(start, end)

}