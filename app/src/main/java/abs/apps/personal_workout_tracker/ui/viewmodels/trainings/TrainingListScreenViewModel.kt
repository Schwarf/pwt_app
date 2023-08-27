package abs.apps.personal_workout_tracker.ui.viewmodels.trainings

import abs.apps.personal_workout_tracker.data.database.Timestamp
import abs.apps.personal_workout_tracker.data.database.Training
import abs.apps.personal_workout_tracker.data.repositories.ITimestampRepository
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


class TrainingListViewModel(
    private val trainingRepository: ITrainingRepository,
    private val timestampRepository: ITimestampRepository
) : ViewModel() {
    val listOftrainingsState: StateFlow<ListOfTrainings> =
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

            val timestamp = Timestamp(
                workoutId = trainingId,
                timestamp = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond()
            )
            timestampRepository.upsertTimestamp(timestamp)
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}

data class ListOfTrainings(val trainingList: List<Training> = listOf())
