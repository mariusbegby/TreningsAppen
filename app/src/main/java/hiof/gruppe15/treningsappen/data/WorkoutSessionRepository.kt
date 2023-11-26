package hiof.gruppe15.treningsappen.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import hiof.gruppe15.treningsappen.model.WorkoutSession

class WorkoutSessionRepository {
    fun saveCompletedSession(workoutSession: WorkoutSession, onComplete: (Boolean, String) -> Unit) {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val firestore = FirebaseFirestore.getInstance()

            firestore.collection("users").document(user.uid)
                .collection("history").add(workoutSession)
                .addOnSuccessListener {
                    onComplete(true, "Workout session saved successfully.")
                }
                .addOnFailureListener { exception ->
                    onComplete(false, exception.message ?: "Unknown error occurred.")
                }
        } else {
            onComplete(false, "User not signed in.")
        }
    }
}