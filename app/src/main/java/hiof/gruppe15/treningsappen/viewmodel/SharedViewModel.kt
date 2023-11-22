package hiof.gruppe15.treningsappen.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import hiof.gruppe15.treningsappen.model.Exercise
import hiof.gruppe15.treningsappen.model.Routine
import hiof.gruppe15.treningsappen.model.WorkoutSession

class SharedViewModel : ViewModel() {
    private val _selectedExercises = mutableStateOf<List<Exercise>>(emptyList())
    val selectedExercises: State<List<Exercise>> = _selectedExercises

    private val _workoutSession = mutableStateOf<WorkoutSession?>(null)
    val workoutSession: State<WorkoutSession?> = _workoutSession

    val routineViewModel = RoutineViewModel()

    private val _isDarkModeEnabled = mutableStateOf(false)
    val isDarkModeEnabled: State<Boolean> = _isDarkModeEnabled

    fun setSelectedExercises(exercises: List<Exercise>) {
        _selectedExercises.value = exercises
    }

    fun toggleDarkMode() {
        _isDarkModeEnabled.value = !isDarkModeEnabled.value
    }

    fun startWorkoutSession(routine: Routine) {
        _workoutSession.value = WorkoutSession(routine)
    }

    fun completeWorkoutSession() {
        // TODO: Implement logic to save the workout session
        _workoutSession.value = null
    }
}