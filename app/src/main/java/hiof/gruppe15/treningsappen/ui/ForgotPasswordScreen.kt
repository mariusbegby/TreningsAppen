package hiof.gruppe15.treningsappen.ui

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import java.net.HttpURLConnection
import java.net.URL

class ForgotPasswordActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ForgotPasswordScreen()
        }
    }
}
@Composable
fun ForgotPasswordScreen(navController: NavController? = null) {
    val context = LocalContext.current
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var shouldSendReset by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Enter email") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            shouldSendReset = true
        }) {
            Text("Send Reset Link")
        }

        if (shouldSendReset) {
            sendResetLink(email.text, context)
            shouldSendReset = false
        }
    }
}

    fun sendResetLink(email: String, context: Context) {
        Toast.makeText(context, "Reset link sent to $email", Toast.LENGTH_SHORT).show()
    }

fun requestPasswordReset(email: String) {
    val urlString = "https://yourbackendapi.com/password-reset" // replace with your backend API endpoint
    val url = URL(urlString)
    val connection = url.openConnection() as HttpURLConnection

    connection.requestMethod = "POST"
    connection.setRequestProperty("Content-Type", "application/json; utf-8")
    connection.setRequestProperty("Accept", "application/json")
    connection.doOutput = true

    val jsonInputString = "{\"email\": \"$email\"}"

    try {
        connection.outputStream.use { os ->
            val input = jsonInputString.toByteArray(Charsets.UTF_8)
            os.write(input, 0, input.size)
        }

        val responseCode = connection.responseCode
        if(responseCode == HttpURLConnection.HTTP_OK) {
            // Password reset request was successful
        } else {
            // Handle error
        }
    } finally {
        connection.disconnect()
    }
}


@Preview(showBackground = true)
@Composable
fun ForgotPasswordScreenPreview() {
    val navController = rememberNavController()
    ForgotPasswordScreen()
}
