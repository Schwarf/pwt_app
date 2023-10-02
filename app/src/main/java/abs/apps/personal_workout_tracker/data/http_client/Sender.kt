package abs.apps.personal_workout_tracker.data.http_client

import abs.apps.personal_workout_tracker.data.database.Workout

class Sender {

    fun checkIsSynchronizationFeasible() : Boolean
    {
        return true;
    }
    
    fun sendWorkout(workout: Workout)
    {
        val workoutDTO = WorkoutDTO(
            name = workout.name,
            sets = workout.sets,
            totalRepetitions = workout.totalRepetitions,
            maxRepetitionsInSet = workout.maxRepetitionsInSet,
            performances = workout.performances,
            id = workout.id,
            isDeleted = workout.isDeleted
        )

    }
}