package abs.apps.personal_workout_tracker.ui.viewmodels.helpers

import abs.apps.personal_workout_tracker.R
import android.provider.Settings.Global.getString

fun getBaseUrl() : String{
    if(isEmulator())
    {
        return getString(R.string.emulator_base_url)
    }
}