package hiof.gruppe15.treningsappen.ui


import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController

@Composable
fun Profile(navController: NavController) {
    Text(text = "This is your personal PRoFILE page",
        textAlign = TextAlign.Center)
}



