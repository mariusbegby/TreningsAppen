package hiof.gruppe15.treningsappen.ui.component.routines

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hiof.gruppe15.treningsappen.model.Routine
import hiof.gruppe15.treningsappen.ui.component.navigation.AppScaffold
import hiof.gruppe15.treningsappen.ui.component.navigation.Screen
import hiof.gruppe15.treningsappen.viewmodel.SharedViewModel

@Composable
fun RoutineScreen(navController: NavController, sharedViewModel: SharedViewModel) {
    val routineViewModel = sharedViewModel.routineViewModel
    routineViewModel.fetchRoutines()
    val routines by routineViewModel.routines.collectAsState()
    val error by routineViewModel.errorState.collectAsState()
    error?.let {
        Text(text = it, color = MaterialTheme.colorScheme.error)
    }

    AppScaffold(navController = navController, title = "Routines") {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            item { Description() }

            item { Spacer(modifier = Modifier.padding(8.dp)) }

            if (sharedViewModel.workoutSession.value != null) {
                item {
                    ContinueSessionButton(onClick = {
                        navController.navigate(Screen.WorkoutSession.route)
                    })
                }

                item { Spacer(modifier = Modifier.padding(8.dp)) }
            }

            item {
                CreateRoutineButton(onClick = {
                    navController.navigate(Screen.CreateNewRoutine.route)
                })
            }

            item { Spacer(modifier = Modifier.padding(8.dp)) }

            if (routines.isEmpty()) {
                item { Spacer(modifier = Modifier.padding(8.dp)) }
                item { Text(text = "You have no routines yet. Create one by clicking the button above.") }
            } else {
                items(routines) { routine ->
                    RoutineCard(navController = navController, routine = routine)
                }
            }
        }
    }
}

@Composable
private fun Description() {
    Text(
        text = "View and edit your routines",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
fun CreateRoutineButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Text(
            text = "Create new routine",
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun ContinueSessionButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Text(
            text = "Continue workout session",
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutineCard(navController: NavController, routine: Routine) {
    Card(
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.elevatedCardColors(),
        onClick = {
            navController.navigate(Screen.RoutineDetails.createRoute(routine.id))
        }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = routine.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Has ${routine.exercises.count()} exercises.",
                style = MaterialTheme.typography.bodyMedium
                    .copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
        }
    }
}