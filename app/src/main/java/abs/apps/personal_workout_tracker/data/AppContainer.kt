package abs.apps.personal_workout_tracker.data

import android.content.Context

interface AppContainer {
    val workoutRepository : IWorkoutRepository
}

class AppDataContainer(private val context: Context): AppContainer
{
    override val workoutRepository: IWorkoutRepository by lazy{
        DatabaseWorkoutRepository(WorkoutDatabase.getDatabase(context =context).workoutDao)
    }

}
