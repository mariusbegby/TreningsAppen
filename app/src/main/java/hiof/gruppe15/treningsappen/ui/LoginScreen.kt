package hiof.gruppe15.treningsappen.ui

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.treningsappen.R
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(navController: NavController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Sign in", style = MaterialTheme.typography.h4, color = Color.Black)
            Text(
                text = "Access your account",
                style = MaterialTheme.typography.h6,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email address") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { passwordFocusRequester.requestFocus() }),
                modifier = Modifier.focusRequester(emailFocusRequester)
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                ),
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_eye_24),
                        contentDescription = "Toggle password visibility",
                        tint = Color.Gray,
                        modifier = Modifier.clickable { passwordVisibility = !passwordVisibility }
                    )
                },
                modifier = Modifier.focusRequester(passwordFocusRequester)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    if (ValidationUtils.isValidEmail(email)) {
                        auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(Screen.Login.route) { inclusive = true }
                                    }
                                } else {
                                    navController.navigate(Screen.WorkOutPlan.route) {
                                        popUpTo(Screen.WorkOutPlan.route) { inclusive = true }
                                    }
                                    /*
                                    Toast.makeText(
                                        context,
                                        "Sign in failed: ${task.exception?.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                     */
                                }
                            }
                    } else {
                        Toast.makeText(
                            context,
                            "Please enter a valid email address",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        context,
                        "Please enter your email and password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }) {
                Text(text = "Sign in")
            }
            Text(
                text = "Forgot Password?",
                modifier = Modifier.clickable {
                    navController.navigate(Screen.ForgotPassword.route)
                }
            )


        }
    }
}
/*@Preview(showBackground = true)*/
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = rememberNavController())
}
