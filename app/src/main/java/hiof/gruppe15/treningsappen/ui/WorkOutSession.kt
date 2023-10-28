package hiof.gruppe15.treningsappen.ui


import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController


@Composable
fun WorkOutSession(navController: NavController) {
    Text(text = "you have just started your workout session",
        textAlign = TextAlign.Center)
}
