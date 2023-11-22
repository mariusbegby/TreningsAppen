package hiof.gruppe15.treningsappen.ui.component.routines

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.treningsappen.R
import hiof.gruppe15.treningsappen.model.RoutineExercise
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
                    WorkoutSessionExerciseCard(sessionExercise, sharedViewModel)
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
fun WorkoutSessionExerciseCard(sessionExercise: WorkoutSessionExercise, sharedViewModel: SharedViewModel) {
    val routineExercise = sessionExercise.routineExercise

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ExerciseDetailsView(sessionExercise.routineExercise) // Display image, name, muscle group

            Spacer(modifier = Modifier.height(8.dp))

            Text("Sets:", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)

            SetInputFields(
                routineExercise = sessionExercise.routineExercise,
                sessionExercise = sessionExercise,
                onLogChange = { index, newLog ->
                    // Create a new list with the updated log
                    val updatedLogs = sessionExercise.setLogs.toMutableList().apply {
                        this[index] = newLog
                    }
                    // Update the state with the new list
                    sharedViewModel.updateSessionExerciseLogs(sessionExercise.routineExercise, updatedLogs)
                }
            )

            if (sessionExercise.setLogs.size < routineExercise.sets) {
                // Button to add a new set, only if less than specified sets
                Button(onClick = { sessionExercise.setLogs.add(WorkoutSessionExercise.SetLog()) }) {
                    Text("Add Set")
                }
            }
        }
    }
}

@Composable
fun ExerciseDetailsView(routineExercise: RoutineExercise) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        if (routineExercise.exercise.imageUrl != null) {
            Image(
                painter = rememberAsyncImagePainter(routineExercise.exercise.imageUrl),
                contentDescription = "Exercise Image",
                modifier = Modifier
                    .size(60.dp)
                    .padding(end = 8.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Fit
            )
        } else {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .padding(end = 8.dp)
                    .clip(RoundedCornerShape(8.dp)),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "App Logo",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }

        Column {
            Text(
                text = routineExercise.exercise.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = routineExercise.exercise.muscleGroup,
                style = MaterialTheme.typography.bodyMedium
            )

            // Display notes if available
            if (routineExercise.note.isNotEmpty()) {
                Text(
                    text = "Notes: ${routineExercise.note}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun SetInputFields(
    routineExercise: RoutineExercise,
    sessionExercise: WorkoutSessionExercise,
    onLogChange: (Int, WorkoutSessionExercise.SetLog) -> Unit
) {
    Column {
        // Display a row for each set log
        sessionExercise.setLogs.forEachIndexed { index, setLog ->
            SetInputRow(setNumber = index + 1, setLog = setLog) { updatedSetLog ->
                onLogChange(index, updatedSetLog)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun SetInputRow(
    setNumber: Int,
    setLog: WorkoutSessionExercise.SetLog,
    onLogChange: (WorkoutSessionExercise.SetLog) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Set $setNumber", style = MaterialTheme.typography.bodyMedium)

        WeightAndRepsInputFields(setLog = setLog) {
            onLogChange(it)
        }
    }
}

@Composable
fun WeightAndRepsInputFields(
    setLog: WorkoutSessionExercise.SetLog,
    onLogChange: (WorkoutSessionExercise.SetLog) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Weight Input
        OutlinedTextField(
            value = setLog.weight,
            onValueChange = { updatedWeight ->
                onLogChange(setLog.copy(weight = updatedWeight))
            },
            label = { Text("Weight") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f) // Give weight input a flex basis
        )
        Spacer(modifier = Modifier.width(8.dp)) // Spacing between the text fields

        // Reps Input
        OutlinedTextField(
            value = setLog.reps,
            onValueChange = { updatedReps ->
                onLogChange(setLog.copy(reps = updatedReps))
            },
            label = { Text("Reps") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f) // Give reps input a flex basis
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