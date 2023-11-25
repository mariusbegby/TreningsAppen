package hiof.gruppe15.treningsappen.ui.component.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.treningsappen.R
import com.google.firebase.auth.FirebaseAuth
import hiof.gruppe15.treningsappen.ui.component.navigation.AppScaffold

@Composable
fun ChangePasswordScreen(navController: NavController) {
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordChangeStatus by remember { mutableStateOf<String?>(null) }

    var newPasswordVisibility by remember { mutableStateOf(false) }
    var confirmPasswordVisibility by remember { mutableStateOf(false) }

    AppScaffold(navController = navController, title = "Change password") {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Text(
                text = "Change your account password",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(24.dp))

            ChangePasswordInputField(
                newPassword = newPassword,
                onPasswordChange = { newPassword = it },
                passwordVisibility = newPasswordVisibility
            ) {
                newPasswordVisibility = !newPasswordVisibility
            }

            Spacer(modifier = Modifier.height(16.dp))

            ChangePasswordConfirmInputField(
                confirmPassword = confirmPassword,
                onPasswordChange = { confirmPassword = it },
                passwordVisibility = confirmPasswordVisibility
            ) {
                confirmPasswordVisibility = !confirmPasswordVisibility
            }

            Spacer(modifier = Modifier.height(24.dp))

            ConfirmChangePasswordButton(onRegisterClick = {
                if (newPassword == confirmPassword) {
                    val user = FirebaseAuth.getInstance().currentUser
                    user?.updatePassword(newPassword)?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            passwordChangeStatus = "Password Updated Successfully"
                            // Navigate back to profile screen after a successful password update
                            navController.navigate("profile") { // Assuming "profile" is the route name
                                popUpTo("profile") { inclusive = true }
                            }
                        } else {
                            passwordChangeStatus =
                                "Failed to Update Password: ${task.exception?.message}"
                        }
                    }
                } else {
                    passwordChangeStatus = "Passwords do not match"
                }
            })
        }
    }
}

@Composable
fun ChangePasswordInputField(newPassword: String, onPasswordChange: (String) -> Unit, passwordVisibility: Boolean, onVisibilityChange: () -> Unit) {
    val passwordFocusRequester = remember { FocusRequester() }

    OutlinedTextField(
        value = newPassword,
        onValueChange = onPasswordChange,
        label = { Text("New password") },
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
        modifier = Modifier
            .focusRequester(passwordFocusRequester)
            .fillMaxWidth()
    )
}

@Composable
fun ChangePasswordConfirmInputField(confirmPassword: String, onPasswordChange: (String) -> Unit, passwordVisibility: Boolean, onVisibilityChange: () -> Unit) {
    val passwordFocusRequester = remember { FocusRequester() }

    OutlinedTextField(
        value = confirmPassword,
        onValueChange = onPasswordChange,
        label = { Text("Confirm new password") },
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
        modifier = Modifier
            .focusRequester(passwordFocusRequester)
            .fillMaxWidth()
    )
}

@Composable
fun ConfirmChangePasswordButton(onRegisterClick: () -> Unit) {
    Button(
        onClick = onRegisterClick, modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Text(
            text = "Confirm password change", color = Color.White, style = MaterialTheme.typography.titleMedium
        )
    }
}