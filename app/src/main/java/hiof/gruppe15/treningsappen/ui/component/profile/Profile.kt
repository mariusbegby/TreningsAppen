package hiof.gruppe15.treningsappen.ui.component.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import hiof.gruppe15.treningsappen.ui.component.navigation.AppScaffold
import hiof.gruppe15.treningsappen.ui.component.navigation.Screen
import hiof.gruppe15.treningsappen.viewmodel.SharedViewModel

@Composable
fun ProfileScreen(navController: NavController, sharedViewModel: SharedViewModel) {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    AppScaffold(navController = navController, title = "Profile") {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            TitleTexts("Profile", "View your profile details and settings")

            Spacer(modifier = Modifier.height(24.dp))

            if (currentUser != null) {
                ProfileDetails(currentUser = currentUser)
            }

            Spacer(modifier = Modifier.height(24.dp))

            ToggleDarkMode(sharedViewModel)

            Spacer(modifier = Modifier.weight(1f))

            DeleteAccountButton(onClick = {
                currentUser?.delete()
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Home.route) { inclusive = true }
                    launchSingleTop = true
                }
            })

            Spacer(modifier = Modifier.height(24.dp))

            LogoutButton(onClick = {
                auth.signOut()
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Home.route) { inclusive = true }
                    launchSingleTop = true
                }
            })

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun TitleTexts(title: String, subtitle: String) {
    Text(text = subtitle, color = MaterialTheme.colorScheme.onSurfaceVariant)
}

@Composable
fun ProfileDetails(currentUser: FirebaseUser) {
    Text(
        text = "Email",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(4.dp))

    if(currentUser.email != null) {
        Text("${currentUser.email}")
    }
}

@Composable
fun LogoutButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Text(
            text = "Sign out",
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun DeleteAccountButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Text(
            text = "Delete my account",
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun ToggleDarkMode(sharedViewModel: SharedViewModel) {
    Text(
        text = "Dark Mode",
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(4.dp))

    Switch(
        checked = sharedViewModel.isDarkModeEnabled.value,
        onCheckedChange = {
            sharedViewModel.toggleDarkMode()
        }
    )
}