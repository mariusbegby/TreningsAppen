package hiof.gruppe15.treningsappen.ui.component.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hiof.gruppe15.treningsappen.ui.component.navigation.AppScaffold
import hiof.gruppe15.treningsappen.ui.component.navigation.Screen

@Composable
fun HomeScreen(navController: NavController) {
    AppScaffold(navController = navController, title = "Home") {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                navController.navigate(Screen.CreateNewRoutine.route)
            }) {
                Text("Create Routine")
            }

            Spacer(modifier = Modifier.padding(8.dp))

            Button(onClick = {
                navController.navigate(Screen.Testing.route)
            }) {
                Text("Testing")
            }

            Spacer(modifier = Modifier.padding(8.dp))

            Box(modifier = Modifier.fillMaxSize()) {
                Column {
                    Text(
                        text = "Saved Routines",
                        style = MaterialTheme.typography.titleMedium,
                    )

                    // Retrieve routines from database and display them here
                    // You can just display the name of the routine in a Text() composable.
                    // Just so we know that it is retrieved from Firebase successfully

                    // TODO: Implement the above logic
                }
            }
        }
    }
}
