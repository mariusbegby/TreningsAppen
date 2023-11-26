package hiof.gruppe15.treningsappen.ui.component.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hiof.gruppe15.treningsappen.ui.component.navigation.AppScaffold
import hiof.gruppe15.treningsappen.ui.component.routines.RoutineExerciseCard
import hiof.gruppe15.treningsappen.viewmodel.SharedViewModel

@Composable
fun HistoryDetailsScreen(navController: NavController, sessionId: String, sharedViewModel: SharedViewModel) {
    val historyViewModel = sharedViewModel.historyViewModel
    val session = historyViewModel.getSessionById(sessionId)

    AppScaffold(navController = navController, title = "Session: ${session?.routine?.name}") {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            session?.let {
                Text(
                    text = "Workout session completed on ${timestampToPrettyDate(it.endTime)}. ${it.exercises.count()} exercises in total with a duration of ${calculateTotalWorkoutDuration(it)}.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(24.dp))

                LazyColumn {
                    items(it.exercises) { exercise ->
                        RoutineExerciseCard(exercise.routineExercise)
                    }
                }
            } ?: Text("Routine was not found")
        }
    }
}
