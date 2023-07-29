package abs.apps.personal_workout_tracker.data.repositories

import abs.apps.personal_workout_tracker.data.database.Performance
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface IPerformancesDao {
    @Upsert
    suspend fun upsertPerformance(performance: Performance)

    @Delete
    suspend fun deletePerformance(performance: Performance)

    @Query("SELECT * FROM performances")
    fun getAllPerformances(): Flow<List<Performance>>

    @Query("SELECT * FROM performances WHERE workoutId = :workoutId")
    fun getAllPerformancesForOneWorkout(workoutId: Int): Flow<Performance>
}