package hiof.gruppe15.treningsappen.ui.component.routines

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            itemsIndexed(workoutSession.exercises) { exerciseIndex, sessionExercise ->
                WorkoutSessionExerciseCard(
                    sessionExercise = sessionExercise,
                    exerciseIndex = exerciseIndex,
                    sharedViewModel = sharedViewModel
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))

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
}

@Composable
fun WorkoutSessionExerciseCard(
    sessionExercise: WorkoutSessionExercise,
    sharedViewModel: SharedViewModel,
    exerciseIndex: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ExerciseDetailsView(sessionExercise.routineExercise)

            Spacer(modifier = Modifier.height(8.dp))

            SetInputHeader()

            sessionExercise.setLogs.forEachIndexed { setIndex, setLog ->
                SetInputRow(
                    setNumber = setIndex + 1,
                    setLog = setLog,
                    onWeightChange = { updatedWeight -> sharedViewModel.updateWeight(exerciseIndex, setIndex, updatedWeight) },
                    onRepsChange = { updatedReps -> sharedViewModel.updateReps(exerciseIndex, setIndex, updatedReps) },
                    onSetComplete = { sharedViewModel.markSetComplete(exerciseIndex, setIndex) }
                )
            }
        }
    }
}

@Composable
fun SetInputHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("SET", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.width(48.dp))
        Text("KG", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.width(48.dp))
        Text("REPS", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.width(48.dp))
        Text("DONE", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.width(40.dp))
    }
}

@Composable
fun SetInputRow(
    setNumber: Int,
    setLog: WorkoutSessionExercise.SetLog,
    onWeightChange: (String) -> Unit,
    onRepsChange: (String) -> Unit,
    onSetComplete: () -> Unit
) {
    val weightState = remember(setNumber) { mutableStateOf(setLog.weight) }
    val repsState = remember(setNumber) { mutableStateOf(setLog.reps) }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("  $setNumber", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.width(32.dp))

        Spacer(Modifier.width(8.dp))

        SmallTextField(
            value = weightState.value,
            onValueChange = {
                weightState.value = it
                onWeightChange(it)
            },
            modifier = Modifier.width(64.dp)
        )

        Spacer(Modifier.width(8.dp))

        SmallTextField(
            value = repsState.value,
            onValueChange = {
                repsState.value = it
                onRepsChange(it)
            },
            modifier = Modifier.width(64.dp)
        )

        Spacer(Modifier.width(8.dp))

        IconButton(onClick = onSetComplete, modifier = Modifier.width(40.dp)) {
            Icon(Icons.Default.Check, contentDescription = "Complete")
        }
    }
}

@Composable
fun SmallTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        modifier = modifier.height(IntrinsicSize.Min),
        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
        shape = RoundedCornerShape(4.dp),
        visualTransformation = VisualTransformation.None
    )
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