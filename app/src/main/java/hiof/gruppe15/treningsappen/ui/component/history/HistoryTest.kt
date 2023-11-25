package hiof.gruppe15.treningsappen.ui.component.history

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import hiof.gruppe15.treningsappen.ui.component.navigation.AppScaffold

@Composable
fun HistoryScreenTest() {
    val navController = rememberNavController() // Create a new NavController
    AppScaffold(navController = navController, title = "History") {
        Text("History Screen")
    }
}