package hiof.gruppe15.treningsappen.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import hiof.gruppe15.treningsappen.model.Routine

class RoutineRepository {
    private val databaseReference = FirebaseDatabase.getInstance().getReference("routines")
    fun saveRoutine(routine: Routine, onComplete: (Boolean, String) -> Unit) {
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
}
