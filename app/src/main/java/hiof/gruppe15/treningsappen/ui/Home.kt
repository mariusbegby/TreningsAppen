package hiof.gruppe15.treningsappen.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController

@Composable
fun Home(navController: NavController) {
    Text(text = "welcome To the home page",
        textAlign = TextAlign.Center)
}


