package hiof.gruppe15.treningsappen


import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import com.google.firebase.database.FirebaseDatabase
import hiof.gruppe15.treningsappen.data.RoutineRepository
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
import hiof.gruppe15.treningsappen.viewmodel.RoutineViewModel

class MainActivity : ComponentActivity() {
    private val routineViewModel by viewModels<RoutineViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

        val repository = RoutineRepository()
        val viewModel = RoutineViewModel(repository)

        setContent {
            TreningsAppenTheme {
                WorkoutApp()
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









