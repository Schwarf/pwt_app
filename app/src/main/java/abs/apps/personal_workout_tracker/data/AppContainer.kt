package abs.apps.personal_workout_tracker.data

import android.content.Context

interface AppContainer {
    val workoutRepository: IWorkoutRepository
    val performanceRepository: IPerformanceRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val workoutRepository: IWorkoutRepository by lazy {
        DatabaseWorkoutRepository(WorkoutDatabase.getDatabase(context = context).workoutDao)
    }

    override val performanceRepository: IPerformanceRepository by lazy {
        DatabasePerformanceRepository(WorkoutDatabase.getDatabase(context = context).performanceDao)
    }

}
