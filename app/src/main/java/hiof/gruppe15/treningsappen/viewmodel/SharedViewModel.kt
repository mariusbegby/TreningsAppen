package hiof.gruppe15.treningsappen.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hiof.gruppe15.treningsappen.data.WorkoutSessionRepository
import hiof.gruppe15.treningsappen.model.Exercise
import hiof.gruppe15.treningsappen.model.Routine
import hiof.gruppe15.treningsappen.model.WorkoutSession
import kotlinx.coroutines.*

class SharedViewModel : ViewModel() {
    private val _selectedExercises = mutableStateOf<List<Exercise>>(emptyList())
    val selectedExercises: State<List<Exercise>> = _selectedExercises

    private val _workoutSession = mutableStateOf<WorkoutSession?>(null)
    val workoutSession: State<WorkoutSession?> = _workoutSession

    val routineViewModel = RoutineViewModel()
    val historyViewModel = HistoryViewModel()

    private val _isDarkModeEnabled = mutableStateOf(false)
    val isDarkModeEnabled: State<Boolean> = _isDarkModeEnabled

    private val _workoutDuration = mutableStateOf("00:00")
    val workoutDuration: State<String> = _workoutDuration

    private var timerJob: Job? = null

    private val workoutSessionRepository = WorkoutSessionRepository()

    fun setSelectedExercises(exercises: List<Exercise>) {
        _selectedExercises.value = exercises
    }

    fun toggleDarkMode() {
        _isDarkModeEnabled.value = !isDarkModeEnabled.value
    }

    fun startWorkoutSession(routine: Routine) {
        _workoutSession.value = WorkoutSession(routine = routine, startTime = System.currentTimeMillis())
        startTimer()
    }

    fun completeWorkoutSession() {
        _workoutSession.value?.let { workoutSession ->
            val completedSession = workoutSession.copy(endTime = System.currentTimeMillis())

            workoutSessionRepository.saveCompletedSession(completedSession) { isSuccess, message ->
            }
        }
        _workoutSession.value = null
        stopTimer()
    }

    private fun startTimer() {
        _workoutDuration.value = "00:00"
        timerJob?.cancel()
        timerJob = viewModelScope.launch(Dispatchers.Default) {
            var time = 0L
            while (isActive) {
                delay(1000)
                time++
                _workoutDuration.value = formatDuration(time)
            }
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        _workoutDuration.value = "00:00"
    }

    private fun formatDuration(seconds: Long): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60

        return when {
            hours > 0 -> String.format("%02d:%02d:%02d", hours, minutes, secs)
            else -> String.format("%02d:%02d", minutes, secs)
        }
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
            }
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