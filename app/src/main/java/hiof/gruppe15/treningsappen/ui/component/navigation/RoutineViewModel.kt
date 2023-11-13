package hiof.gruppe15.treningsappen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hiof.gruppe15.treningsappen.data.RoutineRepository
import hiof.gruppe15.treningsappen.model.Routine
import kotlinx.coroutines.launch

class RoutineViewModel(private val repository: RoutineRepository) : ViewModel() {

    fun saveRoutine(routine: Routine, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            repository.saveRoutine(routine) { isSuccess, message ->
                onResult(isSuccess, message)
            }
        }
    }
}

