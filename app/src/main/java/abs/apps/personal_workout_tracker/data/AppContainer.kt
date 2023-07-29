package abs.apps.personal_workout_tracker.data

import abs.apps.personal_workout_tracker.data.repositories.IWorkoutRepository
import abs.apps.personal_workout_tracker.data.database.TrackerDatabase
import abs.apps.personal_workout_tracker.data.repositories.DatabasePerformanceRepository
import abs.apps.personal_workout_tracker.data.repositories.DatabaseWorkoutRepository
import abs.apps.personal_workout_tracker.data.repositories.IPerformanceRepository
import android.content.Context

interface AppContainer {
    val workoutRepository: IWorkoutRepository
    val performanceRepository: IPerformanceRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val workoutRepository: IWorkoutRepository by lazy {
        DatabaseWorkoutRepository(TrackerDatabase.getDatabase(context = context).workoutDao)
    }

    override val performanceRepository: IPerformanceRepository by lazy {
        DatabasePerformanceRepository(TrackerDatabase.getDatabase(context = context).performanceDao)
    }

}
