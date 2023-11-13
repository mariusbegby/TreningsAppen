package hiof.gruppe15.treningsappen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import hiof.gruppe15.treningsappen.ui.component.auth.ForgotPasswordScreen
import hiof.gruppe15.treningsappen.ui.component.auth.LoginScreen
import hiof.gruppe15.treningsappen.ui.component.auth.RegisterScreen
import hiof.gruppe15.treningsappen.ui.component.home.AnalyticsScreen
import hiof.gruppe15.treningsappen.ui.component.home.HomeScreen
import hiof.gruppe15.treningsappen.ui.component.home.SaveRoutineScreen
import hiof.gruppe15.treningsappen.ui.component.navigation.Screen
import hiof.gruppe15.treningsappen.ui.component.profile.Profile
import hiof.gruppe15.treningsappen.ui.component.settings.SettingsScreen
import hiof.gruppe15.treningsappen.ui.component.workout.RoutineScreen
import hiof.gruppe15.treningsappen.ui.component.workout.WorkoutSessionScreen
import hiof.gruppe15.treningsappen.ui.theme.TreningsAppenTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

        setContent {
            TreningsAppenTheme {
                WorkoutApp()
            }
        }
    }
}

@Composable
fun WorkoutApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Register.route) { RegisterScreen(navController) }
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Profile.route) { Profile(navController) }
        composable(Screen.Settings.route) { SettingsScreen(navController) }
        composable(Screen.WorkOutPlan.route) { RoutineScreen(navController) }
        composable(Screen.WorkOutSession.route) { WorkoutSessionScreen(navController) }
        composable(Screen.Analytics.route) { AnalyticsScreen(navController) }
        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(navController)
        }
        composable(Screen.SaveTrainingRoutine.route) { SaveRoutineScreen(navController) }
    }
}
