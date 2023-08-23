package abs.apps.personal_training_tracker.data.database.dao

import abs.apps.personal_workout_tracker.data.database.Training
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ITrainingsDao {
    @Upsert
    suspend fun upsertTraining(training: Training)

    @Delete
    suspend fun deleteTraining(training: Training)

    @Query("SELECT * FROM trainings ORDER BY name ASC")
    fun getAllTrainings(): Flow<List<Training>>

    @Query("SELECT * from trainings WHERE id = :id")
    fun getTraining(id: Int): Flow<Training>
} 
