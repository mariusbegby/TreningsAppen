package hiof.gruppe15.treningsappen.ui.component.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
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

            AddProfileImageButton { uri ->
                // Save the selected image URI to the user's profile
                //saveImageToUserProfile(uri)
                // Handle the selected image URI as needed
                // For now, let's print the URI
                println("Selected Image URI: $uri")
            }
        }
    }
}

@Composable
fun AddProfileImageButton(
    onImageAdded: (Uri) -> Unit
) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var isImagePickerVisible by remember { mutableStateOf(false) }

    // Function to save the image URI to the user's profile
    val saveImageToUserProfile: (Uri) -> Unit = { uri ->
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            val database = FirebaseDatabase.getInstance().reference
            database.child("users").child(userId).child("profileImageUri").setValue(uri.toString())
        }
        onImageAdded(uri)
    }

    val getContent = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            selectedImageUri = uri
            // Save the selected image URI to the user's profile
            saveImageToUserProfile(uri)
            // Call the callback to handle the selected image URI
            onImageAdded(uri)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        selectedImageUri?.let { uri ->
            Image(
                painter = rememberImagePainter(uri),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
                    .aspectRatio(0.75f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                getContent.launch("image/*")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Add Profile Picture", style = MaterialTheme.typography.titleMedium)
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