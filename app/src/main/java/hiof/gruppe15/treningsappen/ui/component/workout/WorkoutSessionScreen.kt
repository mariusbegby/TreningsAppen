package hiof.gruppe15.treningsappen.ui.component.workout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import hiof.gruppe15.treningsappen.ui.component.navigation.AppScaffold

@Composable
fun WorkoutSessionScreen(navController: NavController) {
    AppScaffold(navController = navController, title = "WorkoutSessionScreen") {
        Column(
            modifier = Modifier.fillMaxSize().padding(it)
        ) {
            Text("WorkoutSession")
        }
    }
}