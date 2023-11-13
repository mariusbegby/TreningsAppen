package hiof.gruppe15.treningsappen.ui.component.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import hiof.gruppe15.treningsappen.ui.component.navigation.AppScaffold

@Composable
fun SettingsScreen(navController: NavController) {
    AppScaffold(navController = navController, title = "SettingsScreen") {
        Column(
            modifier = Modifier.fillMaxSize().padding(it)
        ) {
            Text("Settings")
        }
    }
}
