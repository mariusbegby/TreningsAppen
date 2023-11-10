package hiof.gruppe15.treningsappen.ui.component.navigation

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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hiof.gruppe15.treningsappen.ui.component.workout.AppBottomBar

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object ForgotPassword : Screen("forgotPassword")
    object Home : Screen("home")
    object Profile : Screen("profile")
    object Settings : Screen(route = "setting")
    object WorkOutPlan : Screen(route = "workOutPlan")
    object WorkOutSession : Screen(route = "workOutSession")
    object Analytics : Screen(route = "analytics")
    object Register : Screen("register")
    object DisplayTrainingRoutine: Screen("displaytrainingroutine")
    object SaveTrainingRoutine: Screen("savetrainingroutine")

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveTrainingRoutine(navController: NavController) {
    var routineName by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Go back")
                    }
                }
            )
        },
        bottomBar = { AppBottomBar(navController) }
    ) { innerPadding ->
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
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    navController.navigate(Screen.DisplayTrainingRoutine.route)
                }) {
                    Text("OK")
                }
                Button(onClick = { /* Handle cancel */ }) {
                    Text("Cancel")
                }
            }
        }
    }
}

/*@Composable
fun SaveTrainingRoutine(navController: NavController) {
    var routineName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        IconButton(onClick = {
            navController.navigate(Screen.Home.route) {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
            }
        }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Go back")
        }

        TextField(
            value = routineName,
            onValueChange = { routineName = it },
            label = { Text("Enter routine name...") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {
                navController.navigate(Screen.DisplayTrainingRoutine.route)
            }) {
                Text("OK")
            }
            Button(onClick = { /* Handle cancel */ }) {
                Text("Cancel")
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        AppBottomBar(navController)
    }

}*/

