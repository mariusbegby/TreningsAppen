package hiof.gruppe15.treningsappen.ui.component.profile



import android.net.Uri
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage
import hiof.gruppe15.treningsappen.ui.component.navigation.AppScaffold
import hiof.gruppe15.treningsappen.ui.component.navigation.Screen
import hiof.gruppe15.treningsappen.viewmodel.SharedViewModel

@Composable
fun ProfileScreen(navController: NavController, sharedViewModel: SharedViewModel) {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    val storageRef = FirebaseStorage.getInstance().reference.child("profile_images/$userId.jpg")
    var isImageLoading by remember { mutableStateOf(false) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                imageUri = it
                isImageLoading = true
                saveImageToUserProfile(it) { downloadUri ->
                    imageUri = downloadUri
                    isImageLoading = false
                }
            }
        }
    )

    LaunchedEffect(key1 = userId) {
        storageRef.downloadUrl.addOnSuccessListener { uri ->
            imageUri = uri
        }.addOnFailureListener {
            Log.e("ProfileImageDownload", "Download failed", it)
        }
    }

    AppScaffold(navController = navController, title = "Profile") {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            item {
                TitleTexts("Profile", "View your profile details and settings")
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                ProfileImage(imageUri = imageUri, imagePickerLauncher)
                Spacer(modifier = Modifier.height(24.dp))
            }

            if (currentUser != null) {
                item {
                    ProfileDetails(currentUser = currentUser)
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }

            item { ToggleDarkMode(sharedViewModel) }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item {
                DeleteAccountButton(onClick = {
                    currentUser?.delete()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                        launchSingleTop = true
                    }
                })
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                LogoutButton(onClick = {
                    auth.signOut()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                        launchSingleTop = true
                    }
                })
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun ProfileImage(imageUri: Uri?, imagePickerLauncher: ManagedActivityResultLauncher<String, Uri?>) {
    Box(
        modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
            .background(if (imageUri == null) Color.LightGray else MaterialTheme.colorScheme.primary, shape = CircleShape)
            .clickable { imagePickerLauncher.launch("image/*") },
        contentAlignment = Alignment.Center
    ) {
        if (imageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(imageUri),
                contentDescription = "Profile Image",
                modifier = Modifier.fillMaxSize()
            )
        } else {
            // Display the icon and text when no image is selected
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.AddAPhoto,
                    contentDescription = "Upload Icon",
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Upload your profile pic",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}


fun saveImageToUserProfile(uri: Uri, onImageUploaded: (Uri) -> Unit) {
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
    val storageRef = FirebaseStorage.getInstance().reference.child("profile_images/$userId.jpg")


    storageRef.putFile(uri).addOnSuccessListener { taskSnapshot ->
        taskSnapshot.storage.downloadUrl.addOnSuccessListener { downloadUri ->
            onImageUploaded(downloadUri)
        }
    }.addOnFailureListener { exception ->
        Log.e("ProfileImageUpload", "Upload failed", exception)
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