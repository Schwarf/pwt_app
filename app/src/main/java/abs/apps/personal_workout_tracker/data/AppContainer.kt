package abs.apps.personal_workout_tracker.data

import abs.apps.personal_training_tracker.data.repositories.DatabaseTrainingRepository
import abs.apps.personal_workout_tracker.data.database.TrackerDatabase
import abs.apps.personal_workout_tracker.data.repositories.DatabaseTimestampRepository
import abs.apps.personal_workout_tracker.data.repositories.DatabaseWorkoutRepository
import abs.apps.personal_workout_tracker.data.repositories.ITimestampRepository
import abs.apps.personal_workout_tracker.data.repositories.ITrainingRepository
import abs.apps.personal_workout_tracker.data.repositories.IWorkoutRepository
import android.content.Context

interface AppContainer {
    val workoutRepository: IWorkoutRepository
    val trainingRepository: ITrainingRepository
    val timestampRepository: ITimestampRepository
    
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val workoutRepository: IWorkoutRepository by lazy {
        DatabaseWorkoutRepository(TrackerDatabase.getDatabase(context = context).workoutDao)
    }

    override val timestampRepository: ITimestampRepository by lazy {
        DatabaseTimestampRepository(TrackerDatabase.getDatabase(context = context).timestampDao)
    }
    override val trainingRepository: ITrainingRepository by lazy{
        DatabaseTrainingRepository(TrackerDatabase.getDatabase(context = context).trainingDao)
    }


}
