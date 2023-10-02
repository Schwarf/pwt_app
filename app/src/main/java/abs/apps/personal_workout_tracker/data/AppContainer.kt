package abs.apps.personal_workout_tracker.data

import abs.apps.personal_workout_tracker.data.repositories.DBTrainingRepository
import abs.apps.personal_training_tracker.data.repositories.ITrainingTimestampRepository
import abs.apps.personal_workout_tracker.data.database.TrackerDatabase
import abs.apps.personal_workout_tracker.data.repositories.DBSynchronizationRepository
import abs.apps.personal_workout_tracker.data.repositories.DBTrainingTimestampRepository
import abs.apps.personal_workout_tracker.data.repositories.DBWorkoutRepository
import abs.apps.personal_workout_tracker.data.repositories.DBWorkoutTimestampRepository
import abs.apps.personal_workout_tracker.data.repositories.ISynchronizationRepository
import abs.apps.personal_workout_tracker.data.repositories.ITrainingRepository
import abs.apps.personal_workout_tracker.data.repositories.IWorkoutRepository
import abs.apps.personal_workout_tracker.data.repositories.IWorkoutTimestampRepository
import android.content.Context

interface AppContainer {
    val workoutRepository: IWorkoutRepository
    val trainingRepository: ITrainingRepository
    val workoutTimestampRepository: IWorkoutTimestampRepository
    val trainingTimestampRepository: ITrainingTimestampRepository
    val synchronizationRepository: ISynchronizationRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val workoutRepository: IWorkoutRepository by lazy {
        DBWorkoutRepository(TrackerDatabase.getDatabase(context = context).workoutDao)
    }

    override val workoutTimestampRepository: IWorkoutTimestampRepository by lazy {
        DBWorkoutTimestampRepository(TrackerDatabase.getDatabase(context = context).timestampDao)
    }
    override val trainingRepository: ITrainingRepository by lazy {
        DBTrainingRepository(TrackerDatabase.getDatabase(context = context).trainingDao)
    }
    override val trainingTimestampRepository: ITrainingTimestampRepository by lazy {
        DBTrainingTimestampRepository(TrackerDatabase.getDatabase(context = context).trainingTimestampDao)
    }
    override val synchronizationRepository: ISynchronizationRepository by lazy{
        DBSynchronizationRepository(TrackerDatabase.getDatabase(context = context).synchronizationDao)
    }



}
