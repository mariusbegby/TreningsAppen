package hiof.gruppe15.treningsappen.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import hiof.gruppe15.treningsappen.model.Exercise
import hiof.gruppe15.treningsappen.model.Routine
import hiof.gruppe15.treningsappen.model.RoutineExercise
import hiof.gruppe15.treningsappen.model.WorkoutSession
import hiof.gruppe15.treningsappen.model.WorkoutSessionExercise

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

    fun updateSessionExerciseLogs(routineExercise: RoutineExercise, updatedLogs: List<WorkoutSessionExercise.SetLog>) {
        // Logic to update the logs for the specific exercise
        // Make sure to trigger a state change to recompose the UI
    }

    fun updateWeight(setIndex: Int, exerciseIndex: Int, updatedWeight: String) {
        val currentSession = _workoutSession.value ?: return
        val exercises = currentSession.exercises.toMutableList()
        val setLogs = exercises[exerciseIndex].setLogs.toMutableList()

        setLogs[setIndex] = setLogs[setIndex].copy(weight = updatedWeight)

        exercises[exerciseIndex] = exercises[exerciseIndex].copy(setLogs = setLogs)
        _workoutSession.value = currentSession.copy(exercises = exercises)
    }

    fun updateReps(setIndex: Int, exerciseIndex: Int, updatedReps: String) {
        val currentSession = _workoutSession.value ?: return
        val exercises = currentSession.exercises.toMutableList()
        val setLogs = exercises[exerciseIndex].setLogs.toMutableList()

        setLogs[setIndex] = setLogs[setIndex].copy(reps = updatedReps)

        exercises[exerciseIndex] = exercises[exerciseIndex].copy(setLogs = setLogs)
        _workoutSession.value = currentSession.copy(exercises = exercises)
    }

    fun markSetComplete(setIndex: Int, exerciseIndex: Int) {
        val currentSession = _workoutSession.value ?: return
        val exercises = currentSession.exercises.toMutableList()
        val setLogs = exercises[exerciseIndex].setLogs.toMutableList()

        // Assuming SetLog has a 'completed' property, toggle it
        val currentLog = setLogs[setIndex]
        setLogs[setIndex] = currentLog.copy(completed = !currentLog.completed)

        exercises[exerciseIndex] = exercises[exerciseIndex].copy(setLogs = setLogs)
        _workoutSession.value = currentSession.copy(exercises = exercises)
    }
}