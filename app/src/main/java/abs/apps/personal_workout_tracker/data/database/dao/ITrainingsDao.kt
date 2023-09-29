package abs.apps.personal_training_tracker.data.database.dao

import abs.apps.personal_workout_tracker.data.database.Training
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ITrainingsDao {
    @Upsert
    suspend fun upsertTraining(training: Training)

    @Query("UPDATE trainings SET IsDeleted = 1, lastModified = :lastModified WHERE id = :trainingId")
    suspend fun softDeleteTraining(trainingId: Int, lastModified: Long)

    @Query("SELECT * FROM trainings WHERE isDeleted = 0 ORDER BY name ASC")
    fun getAllTrainings(): Flow<List<Training>>

    @Query("SELECT * from trainings WHERE id = :id")
    fun getTraining(id: Int): Flow<Training>

    @Query(
        "SELECT trainings.* FROM trainings" +
                " INNER JOIN training_timestamps ON trainings.id = training_timestamps.trainingId " +
                " WHERE training_timestamps.timestamp >= :start AND training_timestamps.timestamp < :end " +
                "AND training_timestamps.isDeleted = 0"
    )
    fun getTrainingsByTimestampRange(start: Long, end: Long): Flow<List<Training>>
}
