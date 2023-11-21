package hiof.gruppe15.treningsappen.ui.component.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.treningsappen.R
import com.google.firebase.auth.FirebaseAuth
import hiof.gruppe15.treningsappen.ui.component.navigation.Screen
import hiof.gruppe15.treningsappen.utils.ValidationUtils
import hiof.gruppe15.treningsappen.viewmodel.SharedViewModel

@Composable
fun LoginScreen(navController: NavController, sharedViewModel: SharedViewModel) {
    var email by remember { mutableStateOf("user@example.com") }
    var password by remember { mutableStateOf("123abc123") }
    var passwordVisibility by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    val passwordFocusRequester = remember { FocusRequester() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(96.dp))

            AppLogo()

            Spacer(modifier = Modifier.height(24.dp))

            TitleTexts()

            Spacer(modifier = Modifier.height(24.dp))

            EmailInputField(email, onEmailChange = { email = it }) {
                passwordFocusRequester.requestFocus()
            }

            Spacer(modifier = Modifier.height(16.dp))

            PasswordInputField(password, onPasswordChange = { password = it }, passwordVisibility) {
                passwordVisibility = !passwordVisibility
            }

            Spacer(modifier = Modifier.height(16.dp))

            ForgotPasswordText(onForgotPasswordClick = {
                navController.navigate(Screen.ForgotPassword.route)
            })

            Spacer(modifier = Modifier.height(16.dp))

            LoginButton(onSignInClick = {
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    if (ValidationUtils.isValidEmail(email)) {
                        sharedViewModel.routineViewModel.clearRoutines()

                        auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(Screen.Home.route) { inclusive = true }
                                    }
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Sign in failed: ${task.exception?.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
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

            SignUpText(onSignUpClick = {
                navController.navigate("register")
            })

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun AppLogo() {
    Box(
        modifier = Modifier
            .height(72.dp)
            .width(72.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "App Logo",
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun TitleTexts() {
    Text(text = "Sign in", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = "Login to access your account",
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

@Composable
fun EmailInputField(
    email: String,
    onEmailChange: (String) -> Unit,
    onNext: (KeyboardActionScope.() -> Unit)
) {
    val emailFocusRequester = remember { FocusRequester() }

    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        label = { Text("Email") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(onNext = onNext),
        modifier = Modifier.focusRequester(emailFocusRequester).fillMaxWidth()
    )
}

@Composable
fun PasswordInputField(password: String, onPasswordChange: (String) -> Unit, passwordVisibility: Boolean, onVisibilityChange: () -> Unit) {
    val passwordFocusRequester = remember { FocusRequester() }

    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text("Password") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
        ),
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.baseline_eye_24),
                contentDescription = "Toggle password visibility",
                tint = Color.Gray,
                modifier = Modifier.clickable { onVisibilityChange() }
            )
        },
        modifier = Modifier.focusRequester(passwordFocusRequester).fillMaxWidth()
    )
}

@Composable
fun ForgotPasswordText(onForgotPasswordClick: () -> Unit) {
    Text(
        text = "Forgot Password?",
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.clickable { onForgotPasswordClick() },
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
fun LoginButton(onSignInClick: () -> Unit) {
    Button(
        onClick = onSignInClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun SignUpText(onSignUpClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
    ) {
        Text("Don't have an account? ")
        Text(
            text = "Register",
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.clickable { onSignUpClick() }
        )
    }
}