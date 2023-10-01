package abs.apps.personal_workout_tracker.data.http_client

data class WorkoutDTO(
    var name: String,
    var sets: Int,
    var totalRepetitions: Int,
    var maxRepetitionsInSet: Int,
    var performances: Int,
    val id: Int,
    var isDeleted: Boolean
)

data class TrainingDTO(
    var name: String,
    var durationMinutes: Int,
    var performances: Int,
    val id: Int,
    var isDeleted: Boolean
)

data class WorkoutTimestampDTO(
    var id: Int,
    var workoutId: Int,
    var timestamp: Long,
    var isDeleted: Boolean
)


data class TrainingTimestampDTO(
    var id: Int,
    var trainingId: Int,
    var timestamp: Long,
    var isDeleted: Boolean
)
