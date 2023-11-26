package hiof.gruppe15.treningsappen.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import hiof.gruppe15.treningsappen.model.Routine

class RoutineRepository {
    fun createRoutine(routine: Routine, onComplete: (Boolean, String) -> Unit) {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val firestore = FirebaseFirestore.getInstance()
            firestore.collection("users").document(user.uid)
                .collection("routines").add(routine)
                .addOnSuccessListener {
                    onComplete(true, "Routine saved successfully.")
                }
                .addOnFailureListener { exception ->
                    onComplete(false, exception.message ?: "Unknown error occurred.")
                }
        } else {
            onComplete(false, "User not signed in.")
        }
    }

    fun deleteRoutine(routine: Routine, onComplete: (Boolean, String) -> Unit) {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val firestore = FirebaseFirestore.getInstance()
            firestore.collection("users")
                .document(user.uid)
                .collection("routines")
                .document(routine.id)
                .delete()
                .addOnSuccessListener {
                    onComplete(true, "Routine deleted successfully.")
                }
                .addOnFailureListener { exception ->
                    onComplete(false, exception.message ?: "Unknown error occurred.")
                }
        } else {
            onComplete(false, "User not signed in.")
        }
    }
}
