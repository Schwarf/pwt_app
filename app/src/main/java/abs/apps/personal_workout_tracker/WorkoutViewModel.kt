package abs.apps.personal_workout_tracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WorkoutViewModel(private val dao: WorkoutDao) : ViewModel() {

    private val _state = MutableStateFlow(WorkoutState())

    private val _workouts =
        dao.getWorkouts().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val state =
        combine(_state, _workouts) { state, workouts -> state.copy(workouts = workouts) }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            WorkoutState()
        )

    fun onEvent(event: WorkoutEvent) {
        when (event) {
            is WorkoutEvent.DeleteWorkout -> {
                viewModelScope.launch {
                    dao.deleteWorkout(event.workout)
                }
            }

            WorkoutEvent.HideAddDialog -> _state.update { it.copy(isAddingWorkout = false) }

            WorkoutEvent.SaveWorkout -> {
                val name = state.value.name
                val sets = state.value.sets
                val totalRepetitions = state.value.totalRepetitions
                val maxRepetitionsInSet = state.value.maxRepetitionsInSet
                if (name.isBlank() || sets.isBlank() || maxRepetitionsInSet.isBlank() || totalRepetitions.isBlank()) {
                    return;
                }
                val workout = Workout(
                    name = name,
                    sets = sets,
                    totalRepetitions = totalRepetitions,
                    maxRepetitionsInSets = maxRepetitionsInSet
                )
                viewModelScope.launch { dao.upsertWorkout(workout = workout) }
                _state.update {
                    it.copy(
                        isAddingWorkout = false,
                        name = "",
                        sets = "",
                        maxRepetitionsInSet = "",
                        totalRepetitions = ""
                    )
                }
            }

            is WorkoutEvent.SetMaxRepetitionsInSet -> {
                _state.update { it.copy(maxRepetitionsInSet = event.maxRepetitionsInSet) }
            }

            is WorkoutEvent.SetName -> {
                _state.update { it.copy(name = event.name) }
            }

            is WorkoutEvent.SetSets -> {
                _state.update { it.copy(sets = event.sets) }
            }

            is WorkoutEvent.SetTotalRepetitions -> {
                _state.update { it.copy(totalRepetitions = event.totalRepetitions) }
            }

            WorkoutEvent.ShowAddDialog -> {
                _state.update { it.copy(isAddingWorkout = true) }
            }

            WorkoutEvent.HideChooseActionDialog -> _state.update {
                it.copy(isChoosingAction = false)
            }
            WorkoutEvent.ShowChooseActionDialog -> _state.update {
                it.copy(isChoosingAction = true)
            }
        }

    }
}