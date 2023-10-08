package abs.apps.personal_training_tracker.ui.viewmodels.trainings

import abs.apps.personal_training_tracker.data.repositories.ITrainingTimestampRepository
import abs.apps.personal_workout_tracker.data.database.TrainingTimestamp
import abs.apps.personal_workout_tracker.data.database.toTrainingTimestampUI
import abs.apps.personal_workout_tracker.data.database.toTrainingUI
import abs.apps.personal_workout_tracker.data.repositories.ITrainingRepository
import abs.apps.personal_workout_tracker.ui.screens.trainings.ExistingTrainingDestination
import abs.apps.personal_workout_tracker.ui.viewmodels.dataUI.TrainingTimestamp
import abs.apps.personal_workout_tracker.ui.viewmodels.dataUI.TrainingTimestampUI
import abs.apps.personal_workout_tracker.ui.viewmodels.dataUI.TrainingUI
import abs.apps.personal_workout_tracker.ui.viewmodels.dataUI.toTraining
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId


class ExistingTrainingViewModel(
    savedStateHandle: SavedStateHandle,
    private val trainingRepository: ITrainingRepository,
    private val timestampRepository: ITrainingTimestampRepository
) : ViewModel() {
    private val trainingId: Int =
        checkNotNull(savedStateHandle[ExistingTrainingDestination.trainingIdArg])

    @OptIn(ExperimentalCoroutinesApi::class)
    val existingTrainingsState: StateFlow<ExistingTraining> =
        trainingRepository.getTrainingStream(trainingId).filterNotNull().flatMapLatest { training ->
            timestampRepository.getLatestTimestampStreamForOneTraining(training.id)
                .map { timestamp ->
                    if (timestamp != null)
                        ExistingTraining(
                            trainingUI = training.toTrainingUI(),
                            trainingTimestampUI = timestamp.toTrainingTimestampUI()
                        )
                    else
                        ExistingTraining(
                            trainingUI = training.toTrainingUI().copy(performances = "0"),
                            trainingTimestampUI = TrainingTimestampUI()
                        )

                }
        }.stateIn(
            scope = viewModelScope, started = SharingStarted.WhileSubscribed(
                TIMEOUT_MILLIS
            ), initialValue = ExistingTraining()
        )


    fun addOnePerformance() {
        viewModelScope.launch {
            val currentTraining = existingTrainingsState.value.trainingUI.toTraining()
            trainingRepository.upsertTraining(currentTraining.copy(performances = currentTraining.performances + 1,
                lastModified = System.currentTimeMillis()))
            timestampRepository.upsertTimestamp(
                TrainingTimestamp(
                    trainingId = existingTrainingsState.value.trainingUI.id,
                    timestamp = LocalDateTime.now().atZone(
                        ZoneId.systemDefault(),
                    ).toEpochSecond(),
                    isDeleted = false,
                    lastModified = System.currentTimeMillis()
                )
            )
        }
    }

    fun removeOnePerformance() {
        viewModelScope.launch {
            if (existingTrainingsState.value.trainingUI.toTraining().performances > 0) {
                val currentWorkout = existingTrainingsState.value.trainingUI.toTraining()
                trainingRepository.upsertTraining(currentWorkout.copy(performances = currentWorkout.performances - 1,
                    lastModified = System.currentTimeMillis()))
                timestampRepository.deleteTimestamp(existingTrainingsState.value.trainingTimestampUI.id)
            }
        }
    }


    suspend fun deleteTraining() {
        trainingRepository.deleteTraining(existingTrainingsState.value.trainingUI.id)
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}

data class ExistingTraining(
    val trainingUI: TrainingUI = TrainingUI(),
    val trainingTimestampUI: TrainingTimestampUI = TrainingTimestampUI()
)

