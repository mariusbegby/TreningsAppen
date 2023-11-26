package hiof.gruppe15.treningsappen.ui.component.history

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hiof.gruppe15.treningsappen.model.WorkoutSession
import hiof.gruppe15.treningsappen.ui.component.navigation.AppScaffold
import hiof.gruppe15.treningsappen.ui.component.navigation.Screen
import hiof.gruppe15.treningsappen.viewmodel.SharedViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

@Composable
fun HistoryScreen(navController: NavController, sharedViewModel: SharedViewModel) {
    val historyViewModel = sharedViewModel.historyViewModel
    LaunchedEffect(key1 = Unit) {
        historyViewModel.fetchWorkoutSessions()
    }

    val history by historyViewModel.workoutSessions.collectAsState()
    val sortedHistory = history.sortedByDescending { it.startTime }

    AppScaffold(navController = navController, title = "History") {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            item { Description() }

            item { Spacer(modifier = Modifier.padding(8.dp)) }

            if (history.isEmpty()) {
                item { Text(text = "You have not completed any workouts yet. Head to \"Routines\" to start your first workout.") }
            } else {
                items(sortedHistory) { session ->
                    HistoryCard(navController = navController, session = session)
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
private fun Description() {
    Text(
        text = "View history of your completed workouts",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryCard(navController: NavController, session: WorkoutSession) {
    Card(elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.elevatedCardColors(),
        onClick = {
            navController.navigate(Screen.HistoryDetails.createRoute(session.id))
        }) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = session.routine.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "${session.exercises.count()} exercises completed in ${
                    calculateTotalWorkoutDuration(
                        session
                    )
                }.",
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = timestampToPrettyDate(session.startTime),
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
        }
    }
}

fun timestampToPrettyDate(timestamp: Long): String {
    val date = Date(timestamp)
    val format = SimpleDateFormat("HH:mm EEE. dd MMM yyyy", Locale.getDefault())
    return format.format(date)
}

fun calculateTotalWorkoutDuration(workoutSession: WorkoutSession): String {
    Log.d("WorkoutSession", "calculateTotalWorkoutDuration: $workoutSession")
    val startTime = workoutSession.startTime
    Log.d("WorkoutSession", "startTime: $startTime")
    val endTime = workoutSession.endTime
    Log.d("WorkoutSession", "endTime: $endTime")
    val durationMillis = endTime - startTime
    Log.d("WorkoutSession", "durationMillis: $durationMillis")
    val hours = TimeUnit.MILLISECONDS.toHours(durationMillis)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(durationMillis) % 60
    val seconds = TimeUnit.MILLISECONDS.toSeconds(durationMillis) % 60
    return if (hours > 0) {
        String.format("%02d:%02d:%02d", hours, minutes, seconds)
    } else {
        String.format("%02d:%02d", minutes, seconds)
    }
}