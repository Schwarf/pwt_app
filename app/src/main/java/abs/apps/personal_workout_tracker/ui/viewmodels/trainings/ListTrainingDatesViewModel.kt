package abs.apps.personal_workout_tracker.ui.viewmodels.trainings

import abs.apps.personal_training_tracker.data.repositories.ITrainingTimestampRepository
import abs.apps.personal_workout_tracker.data.database.TrainingTimestamp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListTrainingDatesViewModel(private val trainingTimestampRepository: ITrainingTimestampRepository) :
    ViewModel() {
    private val _timestamps = MutableStateFlow(listOf<TrainingTimestamp>())
    val timestamps: StateFlow<List<TrainingTimestamp>> = _timestamps

    fun getDatesForOneTraining(trainingId: Int) {
        viewModelScope.launch {
            trainingTimestampRepository.getTimestampsStreamForOneTraining(trainingId)
                .collect { fetchedTimestamps ->
                    _timestamps.value = fetchedTimestamps
                }
        }
    }
}