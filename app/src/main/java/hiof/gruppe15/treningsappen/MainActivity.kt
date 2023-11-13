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
import hiof.gruppe15.treningsappen.ui.component.home.Analytic
import hiof.gruppe15.treningsappen.ui.component.home.Home
import hiof.gruppe15.treningsappen.ui.component.home.SaveTrainingRoutine
import hiof.gruppe15.treningsappen.ui.component.navigation.Screen
import hiof.gruppe15.treningsappen.ui.component.profile.Profile
import hiof.gruppe15.treningsappen.ui.component.settings.Settings
import hiof.gruppe15.treningsappen.ui.component.workout.WorkOutPlan
import hiof.gruppe15.treningsappen.ui.component.workout.WorkOutSession
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
        composable(Screen.Register.route) { RegisterScreen(navController) }
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
        composable(Screen.SaveTrainingRoutine.route) { SaveTrainingRoutine(navController) }

    }
    }









