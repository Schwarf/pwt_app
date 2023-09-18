package abs.apps.personal_workout_tracker.data

import abs.apps.personal_training_tracker.data.repositories.DBTrainingRepository
import abs.apps.personal_workout_tracker.data.database.TrackerDatabase
import abs.apps.personal_workout_tracker.data.repositories.DBWorkoutTimestampRepository
import abs.apps.personal_workout_tracker.data.repositories.DBWorkoutRepository
import abs.apps.personal_workout_tracker.data.repositories.IWorkoutTimestampRepository
import abs.apps.personal_workout_tracker.data.repositories.ITrainingRepository
import abs.apps.personal_workout_tracker.data.repositories.IWorkoutRepository
import android.content.Context

interface AppContainer {
    val workoutRepository: IWorkoutRepository
    val trainingRepository: ITrainingRepository
    val timestampRepository: IWorkoutTimestampRepository
    
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val workoutRepository: IWorkoutRepository by lazy {
        DBWorkoutRepository(TrackerDatabase.getDatabase(context = context).workoutDao)
    }

    override val timestampRepository: IWorkoutTimestampRepository by lazy {
        DBWorkoutTimestampRepository(TrackerDatabase.getDatabase(context = context).timestampDao)
    }
    override val trainingRepository: ITrainingRepository by lazy{
        DBTrainingRepository(TrackerDatabase.getDatabase(context = context).trainingDao)
    }


}
