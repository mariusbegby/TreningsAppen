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

    fun updateWeight(exerciseIndex: Int, setIndex: Int, updatedWeight: String) {
        val currentSession = _workoutSession.value ?: return
        val exercises = currentSession.exercises.toMutableList()

        if (exerciseIndex in exercises.indices) {
            val setLogs = exercises[exerciseIndex].setLogs.toMutableList()

            if (setIndex in setLogs.indices) {
                setLogs[setIndex] = setLogs[setIndex].copy(weight = updatedWeight)
                exercises[exerciseIndex] = exercises[exerciseIndex].copy(setLogs = setLogs)
                _workoutSession.value = currentSession.copy(exercises = exercises)
            }
        }
    }

    fun updateReps(exerciseIndex: Int, setIndex: Int, updatedReps: String) {
        val currentSession = _workoutSession.value ?: return
        val exercises = currentSession.exercises.toMutableList()

        if (exerciseIndex in exercises.indices) {
            val setLogs = exercises[exerciseIndex].setLogs.toMutableList()

            if (setIndex in setLogs.indices) {
                setLogs[setIndex] = setLogs[setIndex].copy(reps = updatedReps)
                exercises[exerciseIndex] = exercises[exerciseIndex].copy(setLogs = setLogs)
                _workoutSession.value = currentSession.copy(exercises = exercises)
            } else {
                // Log an error or handle the case where the set index is out of bounds
            }
        } else {
            // Log an error or handle the case where the exercise index is out of bounds
        }
    }

    fun markSetComplete(exerciseIndex: Int, setIndex: Int) {
        val currentSession = _workoutSession.value ?: return
        val exercises = currentSession.exercises.toMutableList()

        if (exerciseIndex in exercises.indices) {
            val setLogs = exercises[exerciseIndex].setLogs.toMutableList()

            if (setIndex in setLogs.indices) {
                val currentLog = setLogs[setIndex]
                setLogs[setIndex] = currentLog.copy(completed = !currentLog.completed)
                exercises[exerciseIndex] = exercises[exerciseIndex].copy(setLogs = setLogs)
                _workoutSession.value = currentSession.copy(exercises = exercises)
            }
        }
    }
}