package hiof.gruppe15.treningsappen.ui


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController


@Composable
fun Profile(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBarProfile(navController)
        Spacer(modifier = Modifier.weight(1f))
        AppBottomBar(navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarProfile(navController: NavController){
    TopAppBar(

        title = { Text("Profil side") },

        )
}



