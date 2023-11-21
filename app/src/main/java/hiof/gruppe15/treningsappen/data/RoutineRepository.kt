package hiof.gruppe15.treningsappen.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import hiof.gruppe15.treningsappen.model.Routine

class RoutineRepository {
    fun createRoutine(routine: Routine, onComplete: (Boolean, String) -> Unit) {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // Reference to Firestore
            val firestore = FirebaseFirestore.getInstance()
            // Creating/Updating the document in 'routines' collection under 'users' collection.
            firestore.collection("users").document(user.uid)
                .collection("routines").add(routine)
                .addOnSuccessListener {
                    // Successfully written to Firestore.
                    onComplete(true, "Routine saved successfully.")
                }
                .addOnFailureListener { exception ->
                    // Handle the error.
                    onComplete(false, exception.message ?: "Unknown error occurred.")
                }
        } else {
            // No user is signed in.
            onComplete(false, "User not signed in.")
        }
    }

    fun deleteRoutine(routine: Routine, onComplete: (Boolean, String) -> Unit) {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // Reference to Firestore
            val firestore = FirebaseFirestore.getInstance()
            // Deleting the document in 'routines' collection under 'users' collection.
            firestore.collection("users").document(user.uid)
                .collection("routines").document(routine.id)
                .delete()
                .addOnSuccessListener {
                    // Successfully deleted from Firestore.
                    onComplete(true, "Routine deleted successfully.")
                }
                .addOnFailureListener { exception ->
                    // Handle the error.
                    onComplete(false, exception.message ?: "Unknown error occurred.")
                }
        } else {
            // No user is signed in.
            onComplete(false, "User not signed in.")
        }
    }

    fun updateRoutine(routine: Routine, onComplete: (Boolean, String) -> Unit) {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // Reference to Firestore
            val firestore = FirebaseFirestore.getInstance()
            // Updating the document in 'routines' collection under 'users' collection.
            firestore.collection("users").document(user.uid)
                .collection("routines").document(routine.id)
                .set(routine)
                .addOnSuccessListener {
                    // Successfully updated in Firestore.
                    onComplete(true, "Routine updated successfully.")
                }
                .addOnFailureListener { exception ->
                    // Handle the error.
                    onComplete(false, exception.message ?: "Unknown error occurred.")
                }
        } else {
            // No user is signed in.
            onComplete(false, "User not signed in.")
        }
    }
}
