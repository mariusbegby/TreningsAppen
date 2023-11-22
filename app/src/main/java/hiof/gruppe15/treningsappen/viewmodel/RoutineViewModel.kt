package hiof.gruppe15.treningsappen.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import hiof.gruppe15.treningsappen.model.Routine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RoutineViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val _routines = MutableStateFlow<List<Routine>>(emptyList())
    val routines = _routines.asStateFlow()
    private val _errorState = MutableStateFlow<String?>(null)
    val errorState = _errorState.asStateFlow()

    init {
        fetchRoutines()
    }

    fun fetchRoutines() {
        viewModelScope.launch {
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null) {
                try {
                    val snapshot = db.collection("users").document(user.uid)
                        .collection("routines").get().await()
                    val routines = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(Routine::class.java)?.copy(id = doc.id)
                    }
                    _routines.value = routines
                    _errorState.value = null
                } catch (e: Exception) {
                    _errorState.value = "Failed to fetch routines: ${e.localizedMessage}"
                    Log.e("RoutineViewModel", "Error fetching routines", e)
                }
            } else {
                _errorState.value = "User not authenticated"
                Log.e("RoutineViewModel", "User not authenticated")
            }
        }
    }

    fun clearRoutines() {
        _routines.value = emptyList()
    }

    fun getRoutineById(id: String): Routine? {
        return _routines.value.find { it.id == id }
    }
}



