package hiof.gruppe15.treningsappen.ui.component.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hiof.gruppe15.treningsappen.model.Routine
import hiof.gruppe15.treningsappen.ui.component.workout.AppBottomBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveRoutineScreen(navController: NavController) {
    val scope = rememberCoroutineScope()
    var routineName by remember { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold(topBar = {
        TopAppBar(title = { Text("") }, navigationIcon = {
            IconButton(onClick = {
                navController.navigateUp()
            }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Go back")
            }
        })
    }, bottomBar = { AppBottomBar(navController) }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = routineName,
                onValueChange = { routineName = it },
                label = { Text("Enter routine name...") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    if (routineName.isNotEmpty()) {
                        scope.launch {
                            /*
                            val routine = Routine(name = routineName)

                            viewModel.saveRoutine(routine) { isSuccess, message ->
                                if (isSuccess) {
                                    navController.navigate(Screen.SavedRoutinesList.route)
                                } else {
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                }
                            }

                             */
                        }
                    } else {
                        Toast.makeText(context, "Routine name cannot be empty", Toast.LENGTH_SHORT)
                            .show()
                    }
                }) {
                    Text("Save")
                }
                Button(onClick = {
                    navController.popBackStack()
                }) {
                    Text("Cancel")
                }
            }
        }
    }
}
