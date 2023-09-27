package abs.apps.personal_workout_tracker.data.repositories

import abs.apps.personal_training_tracker.data.repositories.ITrainingTimestampRepository
import abs.apps.personal_workout_tracker.data.database.TrainingTimestamp
import abs.apps.personal_workout_tracker.data.database.dao.ITrainingTimestampDao
import kotlinx.coroutines.flow.Flow

class DBTrainingTimestampRepository(private val trainingTimestampDao: ITrainingTimestampDao) :

    ITrainingTimestampRepository {
    override fun getAllTimestampsStream(): Flow<List<TrainingTimestamp>> =
        trainingTimestampDao.getAllTimestamps();

    override fun getTimestampsStreamForOneTraining(trainingId: Int): Flow<List<TrainingTimestamp>> =
        getTimestampsStreamForOneTraining(trainingId)

    override fun getLatestTimestampStreamForOneTraining(trainingId: Int): Flow<TrainingTimestamp> =
        trainingTimestampDao.getLatestTimestampForOneTraining(trainingId)

    override suspend fun upsertTimestamp(trainingTimestamp: TrainingTimestamp) =
        trainingTimestampDao.upsertTimestamp(trainingTimestamp)

    override suspend fun deleteTimestamp(trainingTimestamp: TrainingTimestamp) =
        trainingTimestampDao.deleteTimestamp(trainingTimestamp)

}