package hiof.gruppe15.treningsappen.ui.component.routines

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hiof.gruppe15.treningsappen.ui.component.navigation.AppScaffold
import hiof.gruppe15.treningsappen.ui.component.navigation.Screen
import hiof.gruppe15.treningsappen.viewmodel.RoutineViewModel

@Composable
fun RoutineScreen(navController: NavController, routineViewModel: RoutineViewModel) {
    routineViewModel.fetchRoutines()
    val routines by routineViewModel.routines.collectAsState()
    val error by routineViewModel.errorState.collectAsState()
    error?.let {
        Text(text = it, color = MaterialTheme.colorScheme.error)
    }

    AppScaffold(navController = navController, title = "Routines") {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Top
        ) {
            Description()

            Spacer(modifier = Modifier.padding(8.dp))

            CreateRoutineButton(onClick = {
                navController.navigate(Screen.CreateNewRoutine.route)
            })

            Spacer(modifier = Modifier.padding(8.dp))

            LazyColumn(modifier = Modifier
                .fillMaxSize()) {
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