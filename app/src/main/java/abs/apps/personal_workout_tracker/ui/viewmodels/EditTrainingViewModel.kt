package abs.apps.personal_workout_tracker.ui.viewmodels

import abs.apps.personal_workout_tracker.data.database.toValidatedTrainingUI
import abs.apps.personal_workout_tracker.data.repositories.ITrainingRepository
import abs.apps.personal_workout_tracker.ui.screens.EditTrainingDestination
import abs.apps.personal_workout_tracker.ui.viewmodels.dataUI.TrainingUI
import abs.apps.personal_workout_tracker.ui.viewmodels.dataUI.ValidatedTrainingUI
import abs.apps.personal_workout_tracker.ui.viewmodels.dataUI.toTraining
import abs.apps.personal_workout_tracker.ui.viewmodels.dataUI.validateTrainingUI

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditTrainingViewModel(
    savedStateHandle: SavedStateHandle,
    private val TrainingRepository: ITrainingRepository
) : ViewModel() {

    var state by mutableStateOf(ValidatedTrainingUI())
        private set

    private val TrainingId: Int =
        checkNotNull(savedStateHandle[EditTrainingDestination.trainingIdArg])

    init {
        viewModelScope.launch {
            state = TrainingRepository.getTrainingStream(TrainingId)
                .filterNotNull()
                .first()
                .toValidatedTrainingUI(true)
        }
    }

    suspend fun updateTraining() {
        if (validateInput(state.trainingUI)) {
            TrainingRepository.upsertTraining(state.trainingUI.toTraining())
        }
    }

    fun updateUiState(TrainingUI: TrainingUI) {
        state = ValidatedTrainingUI(trainingUI = TrainingUI, isValid = validateInput(TrainingUI))
    }

    private fun validateInput(TrainingUI: TrainingUI = this.state.trainingUI): Boolean {
        return validateTrainingUI(TrainingUI)
    }
}