package hiof.gruppe15.treningsappen.ui.component.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import hiof.gruppe15.treningsappen.ui.component.workout.AppBottomBar


@Composable
fun Settings(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBarSettings(navController)
        Spacer(modifier = Modifier.weight(1f))
        AppBottomBar(navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarSettings(navController: NavController) {
    TopAppBar(title = { androidx.compose.material3.Text("Settings") })
}


