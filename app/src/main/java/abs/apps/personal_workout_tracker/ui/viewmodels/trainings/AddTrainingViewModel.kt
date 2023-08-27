package abs.apps.personal_workout_tracker.ui.viewmodels.trainings

import abs.apps.personal_workout_tracker.data.repositories.ITrainingRepository
import abs.apps.personal_workout_tracker.ui.viewmodels.dataUI.TrainingUI
import abs.apps.personal_workout_tracker.ui.viewmodels.dataUI.ValidatedTrainingUI
import abs.apps.personal_workout_tracker.ui.viewmodels.dataUI.toTraining
import abs.apps.personal_workout_tracker.ui.viewmodels.dataUI.validateTrainingUI
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AddTrainingViewModel(private val trainingRepository: ITrainingRepository) : ViewModel() {
    var state by mutableStateOf(ValidatedTrainingUI())
        private set

    fun updateUiState(trainingUI: TrainingUI) {
        state = ValidatedTrainingUI(
            trainingUI = trainingUI,
            isValid = validateInput(trainingUI)
        )
    }

    suspend fun saveTraining() {
        if (validateInput()) {
            trainingRepository.upsertTraining(state.trainingUI.toTraining())
        }
    }

    private fun validateInput(trainingUI: TrainingUI = this.state.trainingUI) :Boolean
    {
        return validateTrainingUI(trainingUI)
    }
}
