package abs.apps.personal_workout_tracker.ui.viewmodels.trainings

import abs.apps.personal_training_tracker.data.repositories.ITrainingTimestampRepository
import abs.apps.personal_workout_tracker.data.database.WorkoutTimestamp
import abs.apps.personal_workout_tracker.data.database.Training
import abs.apps.personal_workout_tracker.data.database.TrainingTimestamp
import abs.apps.personal_workout_tracker.data.repositories.IWorkoutTimestampRepository
import abs.apps.personal_workout_tracker.data.repositories.ITrainingRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId


class TrainingListScreenViewModel(
    private val trainingRepository: ITrainingRepository,
    private val timestampRepository: ITrainingTimestampRepository
) : ViewModel() {
    val listOfTrainingsState: StateFlow<ListOfTrainings> =
        trainingRepository.getAllTrainingsStream().map { ListOfTrainings(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ListOfTrainings()
            )

    fun addPerformance(trainingId: Int) {
        viewModelScope.launch {
            val currenttraining = trainingRepository.getTrainingStream(trainingId).firstOrNull()

            currenttraining?.let {
                val updatedPerformances = it.performances + 1
                trainingRepository.updateTrainingPerformances(trainingId, updatedPerformances)
            }

            val trainingTimestamp = TrainingTimestamp(
                trainingId = trainingId,
                timestamp = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond(),
                isDeleted = false,
                lastModified = System.currentTimeMillis()
            )
            timestampRepository.upsertTimestamp(trainingTimestamp)
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}

data class ListOfTrainings(val trainingList: List<Training> = listOf())
