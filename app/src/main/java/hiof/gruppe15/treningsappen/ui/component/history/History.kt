package hiof.gruppe15.treningsappen.ui.component.history

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hiof.gruppe15.treningsappen.ui.component.navigation.AppScaffold
import hiof.gruppe15.treningsappen.viewmodel.SharedViewModel

@Composable
fun HistoryScreen(navController: NavController, sharedViewModel: SharedViewModel) {
    val historyViewModel = sharedViewModel.historyViewModel
    LaunchedEffect(key1 = Unit) {
        historyViewModel.fetchWorkoutSessions()
    }

    val history by historyViewModel.workoutSessions.collectAsState()

    AppScaffold(navController = navController, title = "History") {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Log.d("HistoryScreen", "History sessions count: ${history.size}")

            if (history.isEmpty()) {
                Text("No saved sessions found in history", color = MaterialTheme.colorScheme.error)
            } else {
                Text(text = "found ${history.size} sessions")

                LazyColumn {
                    items(history) { session ->
                        Log.d("HistoryScreen", "Displaying session: ${session.id}")
                        Text(text = session.routine.name)
                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }
    }
}