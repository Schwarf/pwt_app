package abs.apps.personal_workout_tracker

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    @Upsert
    suspend fun upsertWorkout(workout: Workout)

    @Delete
    suspend fun deleteWorkout(workout: Workout)

    @Query("SELECT * FROM workout ORDER BY name ASC")
    fun getWorkout(): Flow<List<Workout>>
}