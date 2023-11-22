package hiof.gruppe15.treningsappen.ui.component.profile



import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
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

    LaunchedEffect(key1 = userId) {
        val storageRef = FirebaseStorage.getInstance().reference.child("profile_images/$userId.jpg")
        storageRef.downloadUrl.addOnSuccessListener { uri ->
            imageUri = uri
        }.addOnFailureListener {
            // Handle the error, show a placeholder or a default image
        }
    }

    AppScaffold(navController = navController, title = "Profile") {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            TitleTexts("Profile", "View your profile details and settings")

            Spacer(modifier = Modifier.height(24.dp))

            ProfileImage(imageUri = imageUri)

            Spacer(modifier = Modifier.height(24.dp))

            AddProfileImageButton { uri ->
                imageUri = uri
                println("Selected Image URI: $uri")
            }

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
fun ProfileImage(imageUri: Uri?)  {
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    val storageRef = FirebaseStorage.getInstance().reference.child("profile_images/$userId.jpg")

    if (imageUri == null) {
        // Display a placeholder
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color.LightGray, shape = CircleShape)
        )
    } else {
        Image(
            painter = rememberAsyncImagePainter(imageUri),
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
        )
    }
}

@Composable
fun AddProfileImageButton(
    onImageAdded: (Uri) -> Unit
) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val saveImageToUserProfile: (Uri) -> Unit = { uri ->
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            val storageRef = FirebaseStorage.getInstance().reference.child("profile_images/$userId.jpg")

            storageRef.putFile(uri)
                .addOnSuccessListener { taskSnapshot ->

                    taskSnapshot.storage.downloadUrl.addOnSuccessListener { downloadUri ->
                        val imageUrl = downloadUri.toString()

                        val database = FirebaseDatabase.getInstance().reference
                        database.child("users").child(userId).child("profileImageUri").setValue(imageUrl)
                    }
                }
                .addOnFailureListener { exception: Exception ->
                    Log.e("ProfileImageUpload", "Upload failed", exception)
                }
        }
    }

    val getContent = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            saveImageToUserProfile(it) { downloadUri ->
                onImageAdded(downloadUri)
            }
        }
    }

    Button(
        onClick = { getContent.launch("image/*") },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Text("Add profile image", style = MaterialTheme.typography.titleMedium)
    }


    selectedImageUri?.let { uri ->
        Image(
            painter = rememberAsyncImagePainter(uri),
            contentDescription = null,
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
                .aspectRatio(0.75f)
        )

        Spacer(modifier = Modifier.height(8.dp))
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