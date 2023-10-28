package hiof.gruppe15.treningsappen.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Home() {
    Text(text = "welcome To the home page",
        textAlign = TextAlign.Center)
}

@Preview
@Composable
fun HomePreview(){
    Home()
}
