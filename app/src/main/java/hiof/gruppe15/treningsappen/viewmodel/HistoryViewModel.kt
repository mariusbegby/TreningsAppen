package hiof.gruppe15.treningsappen.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import hiof.gruppe15.treningsappen.model.WorkoutSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class HistoryViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val _workoutSessions = MutableStateFlow<List<WorkoutSession>>(emptyList())
    val workoutSessions = _workoutSessions.asStateFlow()

    fun fetchWorkoutSessions() {
        viewModelScope.launch {
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null) {
                try {
                    val snapshot = db.collection("users").document(user.uid)
                        .collection("history").get().await()

                    val sessions = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(WorkoutSession::class.java)?.copy(id = doc.id)
                    }

                    _workoutSessions.value = sessions
                } catch (e: Exception) {
                    Log.e("HistoryViewModel", "Error fetching sessions", e)
                }
            } else {
                Log.e("HistoryViewModel", "User not authenticated")
            }
        }
    }

    fun getSessionById(id: String): WorkoutSession? {
        return _workoutSessions.value.find { it.id == id }
    }
}