package abs.apps.personal_workout_tracker.ui.viewmodels.synchronization


import abs.apps.personal_workout_tracker.data.http_client.Sender
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SynchronizationViewModel(private val sender: Sender) : ViewModel() {
    // Define LiveData or State for the synchronization result
    private val isFeasible = mutableStateOf(false)


    fun convertAndSend() {
        viewModelScope.launch {  sender.convertAndSend()}
    }
}