package hiof.gruppe15.treningsappen.ui.component.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import hiof.gruppe15.treningsappen.ui.component.navigation.Screen
import hiof.gruppe15.treningsappen.utils.ValidationUtils

@Composable
fun RegisterScreen(navController: NavController) {
    val context = LocalContext.current
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

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

            TitleTexts("Create Account", "Sign up to get started")

            Spacer(modifier = Modifier.height(24.dp))

            EmailInputField(email, onEmailChange = { email = it }) {

            }

            Spacer(modifier = Modifier.height(16.dp))

            PasswordInputField(password, onPasswordChange = { password = it }, passwordVisibility) {
                passwordVisibility = !passwordVisibility
            }

            Spacer(modifier = Modifier.height(36.dp))

            RegisterButton(onRegisterClick = {
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    if (ValidationUtils.isValidEmail(email)) {
                        if (ValidationUtils.isPasswordStrong(password)) {
                            auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Toast.makeText(
                                            context, "Registration successful", Toast.LENGTH_SHORT
                                        ).show()
                                        navController.navigate(Screen.Login.route) {
                                            popUpTo(Screen.Register.route) { inclusive = true }
                                        }
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Registration failed: ${task.exception?.message}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                        } else {
                            Toast.makeText(
                                context,
                                "Password should be at least 8 characters and contain a mix of letters and numbers",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            context, "Please enter a valid email address", Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        context, "Please enter your email and password", Toast.LENGTH_SHORT
                    ).show()
                }
            })

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun RegisterButton(onRegisterClick: () -> Unit) {
    Button(
        onClick = onRegisterClick, modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Text(
            text = "Register", color = Color.White, style = MaterialTheme.typography.titleMedium
        )
    }
}