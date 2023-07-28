package abs.apps.personal_workout_tracker

import abs.apps.personal_workout_tracker.data.AppContainer
import abs.apps.personal_workout_tracker.data.AppDataContainer
import android.app.Application

class WorkoutTrackerApplication : Application() {
    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }

}