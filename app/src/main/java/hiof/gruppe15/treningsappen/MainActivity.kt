package hiof.gruppe15.treningsappen


import androidx.activity.viewModels
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import hiof.gruppe15.treningsappen.ui.component.navigation.NavGraph
import hiof.gruppe15.treningsappen.ui.component.navigation.Screen
import hiof.gruppe15.treningsappen.ui.theme.AppTheme
import hiof.gruppe15.treningsappen.viewmodel.SharedViewModel

class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedViewModel: SharedViewModel by viewModels()

        FirebaseApp.initializeApp(this)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

        setContent {
            AppTheme {
                navController = rememberNavController()

                NavGraph(
                    navController = navController,
                    startDestination = Screen.Login.route,
                    sharedViewModel = sharedViewModel
                )
            }
        }
    }
}