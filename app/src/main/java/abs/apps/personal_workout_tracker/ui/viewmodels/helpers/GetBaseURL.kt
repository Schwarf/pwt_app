package abs.apps.personal_workout_tracker.ui.viewmodels.helpers

fun getBaseUrl(endpoint: String): String {
    val baseUrl = "http://192.168.0.227:8000/"
    val emulatorBaseUrl = "http://10.0.2.2:8000/"
    val endPoints = mapOf(
        "workouts" to "insert_workouts/",
        "trainings" to "insert_trainings/",
        "workout_timestamps" to "insert_workout_timestamps/",
        "training_timestamps" to "insert_training_timestamps/"
    )
    if (isEmulator()) {
        return emulatorBaseUrl+endPoints[endpoint]
    }
    return baseUrl+endPoints[endpoint]
}

