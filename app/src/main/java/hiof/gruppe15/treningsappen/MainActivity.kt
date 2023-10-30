package hiof.gruppe15.treningsappen

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import hiof.gruppe15.treningsappen.ui.Analytic
import hiof.gruppe15.treningsappen.ui.ForgotPasswordScreen
import hiof.gruppe15.treningsappen.ui.Home
import hiof.gruppe15.treningsappen.ui.LoginScreen
import hiof.gruppe15.treningsappen.ui.Profile
import hiof.gruppe15.treningsappen.ui.ProfileScreen
import hiof.gruppe15.treningsappen.ui.Screen
import hiof.gruppe15.treningsappen.ui.Settings
import hiof.gruppe15.treningsappen.ui.WorkOutPlan
import hiof.gruppe15.treningsappen.ui.WorkOutSession
import hiof.gruppe15.treningsappen.ui.theme.TreningsAppenTheme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            TreningsAppenTheme {
                NavigasjonApp()
            }
        }
    }
}

@Composable
fun AppFloatingActionButton() {
    val context = LocalContext.current

    FloatingActionButton(onClick = {
        Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
    }) {
        Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.Blue)
    }
}


@Composable
fun NavigasjonApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Profile.route) { ProfileScreen(navController) }
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.Home.route) { Home(navController) }
        composable(Screen.Profile.route) { Profile(navController) }
        composable(Screen.Settings.route) { Settings(navController) }
        composable(Screen.WorkOutPlan.route) { WorkOutPlan(navController) }
        composable(Screen.WorkOutSession.route) { WorkOutSession(navController) }
        composable(Screen.Analytics.route) { Analytic(navController) }
        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(navController)
        }
        }
    }





