package hiof.gruppe15.treningsappen.ui.component.routines

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import hiof.gruppe15.treningsappen.ui.component.navigation.AppScaffold
import hiof.gruppe15.treningsappen.viewmodel.RoutineViewModel

@Composable
fun RoutineScreen(navController: NavController, routineViewModel: RoutineViewModel) {
    val routines by routineViewModel.routines.collectAsState()
    val error by routineViewModel.errorState.collectAsState()
    error?.let {
        Text(text = it, color = MaterialTheme.colorScheme.error)
    }

    AppScaffold(navController = navController, title = "Routines") {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            routines.forEach { routine ->
                RoutineCard(navController = navController, routine = routine)
            }
        }
    }
}
