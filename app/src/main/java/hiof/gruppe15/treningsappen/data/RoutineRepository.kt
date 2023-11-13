package hiof.gruppe15.treningsappen.data

import com.google.firebase.database.FirebaseDatabase
import hiof.gruppe15.treningsappen.model.Routine

class RoutineRepository {
    private val databaseReference = FirebaseDatabase.getInstance().getReference("routines")
    fun saveRoutine(routine: Routine, onResult: (Boolean, String) -> Unit) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("routines")
        val routineId = databaseReference.push().key ?: run {
            onResult(false, "Failed to get Firebase key for routine.")
            return
        }
        val routineToSave = routine.copy(id = routineId)
        databaseReference.child(routineId).setValue(routineToSave).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onResult(true, routineId)
            } else {
                onResult(false, task.exception?.message ?: "Error saving routine.")
            }
        }
    }
}
