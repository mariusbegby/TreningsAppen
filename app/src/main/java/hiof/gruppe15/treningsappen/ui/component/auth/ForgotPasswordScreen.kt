package hiof.gruppe15.treningsappen.ui.component.auth

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ForgotPasswordScreen(navController: NavController? = null) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var shouldSendReset by remember { mutableStateOf(false) }

    TopBar(navController)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(96.dp))

            AppLogo()

            Spacer(modifier = Modifier.height(24.dp))

            TitleTexts("Forgot password", "Enter email to reset your password")

            Spacer(modifier = Modifier.height(24.dp))

            EmailInputField(email, onEmailChange = { email = it }) {

            }

            Spacer(modifier = Modifier.height(36.dp))

            SendResetButton(onClick = {
                if (email.isNotEmpty()) {
                    shouldSendReset = true
                } else {
                    Toast.makeText(
                        context, "Please enter your email", Toast.LENGTH_SHORT
                    ).show()
                }
            })

            if (shouldSendReset) {
                sendResetLink(email, context)
                shouldSendReset = false
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun TitleTexts(title: String, subtitle: String) {
    Text(text = title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(8.dp))
    Text(text = subtitle, color = MaterialTheme.colorScheme.onSurfaceVariant)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController?) {
    TopAppBar(
        title = { Text("") },
        navigationIcon = {
            if (navController != null) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        }
    )
}

@Composable
fun EmailInputField(email: TextFieldValue, onEmailChange: (TextFieldValue) -> Unit) {
    TextField(
        value = email,
        onValueChange = onEmailChange,
        label = { Text("Enter email") }
    )
}

@Composable
fun SendResetButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)) {
        Text(
            text = "Send reset link",
            style = MaterialTheme.typography.titleMedium
        )
    }
}

fun sendResetLink(email: String, context: Context) {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            Toast.makeText(context, "Reset link sent to $email", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT)
                .show()
        }
    }
}
