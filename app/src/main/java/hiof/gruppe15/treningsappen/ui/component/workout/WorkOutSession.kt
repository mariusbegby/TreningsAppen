package hiof.gruppe15.treningsappen.ui.component.workout


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController


@Composable
fun WorkOutSession(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBarWorkOutSession(navController)
        Spacer(modifier = Modifier.weight(1f))
        AppBottomBar(navController)
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWorkOutSession(navController: NavController){
    TopAppBar(

        title = { androidx.compose.material3.Text("Workout Session") },

        )
}
