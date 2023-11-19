package hiof.gruppe15.treningsappen.viewmodel

import androidx.lifecycle.ViewModel
import hiof.gruppe15.treningsappen.data.RoutineRepository

class RoutineViewModel(private val repository: RoutineRepository) : ViewModel() {

    /*
    fun saveRoutine(routine: Routine) {
        val _routineSaveStatus = MutableLiveData<String>()
        val routineSaveStatus: LiveData<String> = _routineSaveStatus

        fun saveRoutine(routine: Routine) {
            val currentUser = FirebaseAuth.getInstance().currentUser
            if (currentUser != null) {
                val databaseReference =
                    FirebaseDatabase.getInstance().getReference("users/${currentUser.uid}/routines")
                databaseReference.push().setValue(routine)
                    .addOnSuccessListener {
                        _routineSaveStatus.postValue("Routine saved successfully.")
                    }
                    .addOnFailureListener { e ->
                        _routineSaveStatus.postValue(
                            e.message ?: "Error occurred while saving routine."
                        )
                    }
            } else {
                _routineSaveStatus.postValue("User not authenticated.")
            }
        }
    }*/
}

