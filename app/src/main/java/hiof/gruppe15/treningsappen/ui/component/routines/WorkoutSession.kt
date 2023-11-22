package hiof.gruppe15.treningsappen.ui.component.routines

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hiof.gruppe15.treningsappen.model.WorkoutSessionExercise
import hiof.gruppe15.treningsappen.ui.component.navigation.AppScaffold
import hiof.gruppe15.treningsappen.viewmodel.SharedViewModel

@Composable
fun WorkoutSessionScreen(navController: NavController, sharedViewModel: SharedViewModel) {
    val workoutSession = sharedViewModel.workoutSession.value ?: return

    AppScaffold(navController = navController, title = "Session: ${workoutSession.routine.name}") {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            LazyColumn {
                items(workoutSession.exercises) { sessionExercise ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = sessionExercise.exercise.name,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            // Display the input fields for each set
                            sessionExercise.setLogs.forEachIndexed { index, setLog ->
                                SetInputFields(index + 1, setLog)
                            }
                            // Button to add a new set
                            Button(onClick = { sessionExercise.setLogs.add(WorkoutSessionExercise.SetLog()) }) {
                                Text("Add Set")
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            CompleteWorkoutButton(
                onClick = {
                    sharedViewModel.completeWorkoutSession()
                    navController.popBackStack()
                }
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun SetInputFields(setNumber: Int, setLog: WorkoutSessionExercise.SetLog) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Set $setNumber", style = MaterialTheme.typography.bodyMedium)
        OutlinedTextField(
            value = setLog.weight,
            onValueChange = { setLog.weight = it },
            label = { Text("Weight") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        OutlinedTextField(
            value = setLog.reps,
            onValueChange = { setLog.reps = it },
            label = { Text("Reps") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}

@Composable
fun CompleteWorkoutButton(onClick: () -> Unit) {
    val context = LocalContext.current

    Button(
        onClick = {
            onClick()
            Toast.makeText(context, "Workout Completed!", Toast.LENGTH_SHORT).show()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Text(
            text = "Complete workout session",
            style = MaterialTheme.typography.titleMedium
        )
    }
}