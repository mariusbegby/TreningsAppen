package hiof.gruppe15.treningsappen.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import hiof.gruppe15.treningsappen.model.Exercise

class SharedViewModel : ViewModel() {
    private val _selectedExercises = mutableStateOf<List<Exercise>>(emptyList())
    val selectedExercises: State<List<Exercise>> = _selectedExercises

    val routineViewModel = RoutineViewModel()

    private val _isDarkModeEnabled = mutableStateOf(false)
    val isDarkModeEnabled: State<Boolean> = _isDarkModeEnabled

    fun setSelectedExercises(exercises: List<Exercise>) {
        _selectedExercises.value = exercises
    }

    fun toggleDarkMode() {
        _isDarkModeEnabled.value = !isDarkModeEnabled.value
    }
}