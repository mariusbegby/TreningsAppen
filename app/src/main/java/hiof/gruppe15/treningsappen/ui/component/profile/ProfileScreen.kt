package hiof.gruppe15.treningsappen.ui.component.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import hiof.gruppe15.treningsappen.ui.component.navigation.Screen

@Composable
fun ProfileScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    Column {
        Text("Profile")
        Text("Email: ${currentUser?.email}")
        Button(onClick = {
            auth.signOut()
            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Home.route) { inclusive = true }
            }
        }) {
            Text("Log Out")
        }
    }
}
