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
import hiof.gruppe15.treningsappen.ui.Home
import hiof.gruppe15.treningsappen.ui.LoginScreen
import hiof.gruppe15.treningsappen.ui.Profile
import hiof.gruppe15.treningsappen.ui.Settings
import hiof.gruppe15.treningsappen.ui.WorkOutPlan
import hiof.gruppe15.treningsappen.ui.WorkOutSession
import hiof.gruppe15.treningsappen.ui.theme.TreningsAppenTheme




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TreningsAppenTheme {
                }
            NavigasjonApp()
            }
        }
    }

@Composable
fun AppFloatingActionButton() {
    val context = LocalContext.current

    FloatingActionButton(
        onClick = {

            Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
        }
    ) {
       Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.Blue)
    }
}


@Composable
fun NavigasjonApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(login = { navController.navigate("WorkOutPlan") }) }
        composable("home") { Home() }
        composable("profile") { Profile() }
        composable("settings") { Settings() }
        composable("workoutplan") { WorkOutPlan() }
        composable("workoutsession") { WorkOutSession() }
    }
}

