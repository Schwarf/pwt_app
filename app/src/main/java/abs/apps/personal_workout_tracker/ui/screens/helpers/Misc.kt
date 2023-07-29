package abs.apps.personal_workout_tracker.ui.screens.helpers

fun removeWhitespaces(input: String): String {
    return input.replace("\\s".toRegex(), "")
}

fun removeNonDigits(input: String): String {
    return input.replace("\\D".toRegex(), "")
}

fun removeNonAlphanumeric(input: String): String {
    return input.replace("\\W|_".toRegex(), "")
}
