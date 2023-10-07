package abs.apps.personal_workout_tracker.data.repositories

import abs.apps.personal_workout_tracker.data.database.Training
import abs.apps.personal_workout_tracker.data.database.WorkoutTimestamp
import kotlinx.coroutines.flow.Flow

interface IWorkoutTimestampRepository {
    fun getAllTimestampsStream(): Flow<List<WorkoutTimestamp>>

    fun getTimestampsStreamForOneWorkout(workoutId: Int): Flow<List<WorkoutTimestamp>>

    fun getLatestTimestampStreamForOneWorkout(workoutId: Int): Flow<WorkoutTimestamp>
    suspend fun upsertTimestamp(workoutTimestamp: WorkoutTimestamp)
    suspend fun deleteTimestamp(id: Int)
    suspend fun getUpdatesForSynchronization(lastSynchronizationTimestamp: Long): List<WorkoutTimestamp>
}