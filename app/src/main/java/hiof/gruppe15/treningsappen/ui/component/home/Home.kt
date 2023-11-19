package hiof.gruppe15.treningsappen.ui.component.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import hiof.gruppe15.treningsappen.ui.component.navigation.AppScaffold
import hiof.gruppe15.treningsappen.ui.component.navigation.Screen
import hiof.gruppe15.treningsappen.viewmodel.RoutineViewModel

@Composable
fun HomeScreen(navController: NavController, routineViewModel: RoutineViewModel = viewModel()) {
    // Retrieve the list of routines from the viewmodel
    val routines by routineViewModel.routines.collectAsState()
    val error by routineViewModel.errorState.collectAsState()
    val routineViewModel: RoutineViewModel = viewModel()
    val errorMessage by routineViewModel.errorState.collectAsState()
    error?.let {
        Text(text = it, color = MaterialTheme.colorScheme.error)
    }

    var expandedRoutineIds by remember { mutableStateOf(setOf<String>()) }

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

            if (errorMessage != null) {
                Text(text = errorMessage!!, color = MaterialTheme.colorScheme.error)
            }

            Box(modifier = Modifier.fillMaxSize()) {
                Column {
                    Text(
                        text = "Saved Routines",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    routines.forEach { routine ->
                        Text(
                            text = routine.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    // Toggle the expanded state for this routine
                                    expandedRoutineIds = if (routine.id in expandedRoutineIds) {
                                        expandedRoutineIds - routine.id
                                    } else {
                                        expandedRoutineIds + routine.id
                                    }
                                }
                                .padding(8.dp)
                        )
                        if (routine.id in expandedRoutineIds) {
                            routine.exercises.forEach { exercise ->
                                Text(
                                    text = " - ${exercise.name}",
                                    modifier = Modifier.padding(start = 16.dp)
                                )
                            }
                        }
                    }
                    error?.let {
                        Text(text = it, color = MaterialTheme.colorScheme.error)
                    }
                }
                }
            }
        }
    }

