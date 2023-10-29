package abs.apps.personal_workout_tracker.ui.viewmodels.trainings

import abs.apps.personal_training_tracker.data.repositories.ITrainingTimestampRepository
import abs.apps.personal_workout_tracker.data.database.TrainingTimestamp
import abs.apps.personal_workout_tracker.data.database.toTrainingUI
import abs.apps.personal_workout_tracker.data.database.toWorkoutUI
import abs.apps.personal_workout_tracker.data.repositories.ITrainingRepository
import abs.apps.personal_workout_tracker.ui.viewmodels.dataUI.TrainingUI
import abs.apps.personal_workout_tracker.ui.viewmodels.dataUI.WorkoutUI
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class TrainingDatesViewModel(private val trainingTimestampRepository: ITrainingTimestampRepository,
    private val trainingRepository: ITrainingRepository) :
    ViewModel() {
    private val _timestamps = MutableStateFlow(listOf<TrainingTimestamp>())
    val timestamps: StateFlow<List<TrainingTimestamp>> = _timestamps
    var training by mutableStateOf(TrainingUI())
        private set


    fun getDatesForOneTraining(trainingId: Int) {
        viewModelScope.launch {
            trainingTimestampRepository.getTimestampsStreamForOneTraining(trainingId)
                .collect { fetchedTimestamps ->
                    _timestamps.value = fetchedTimestamps
                }
        }
    }

    fun getTraining(trainingId: Int) {
        viewModelScope.launch {
            training = trainingRepository.getTrainingStream(trainingId)
                .filterNotNull()
                .first()
                .toTrainingUI()
        }
    }

}