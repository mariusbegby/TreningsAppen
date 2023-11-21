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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hiof.gruppe15.treningsappen.data.RoutineRepository
import hiof.gruppe15.treningsappen.model.Exercise
import hiof.gruppe15.treningsappen.model.Routine
import hiof.gruppe15.treningsappen.model.RoutineExercise
import hiof.gruppe15.treningsappen.ui.component.navigation.AppScaffold
import hiof.gruppe15.treningsappen.ui.component.navigation.Screen
import hiof.gruppe15.treningsappen.viewmodel.SharedViewModel

@Composable
fun SaveRoutineScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel
) {
    var routineName by remember { mutableStateOf("") }
    val context = LocalContext.current
    val selectedExercises = sharedViewModel.selectedExercises.value
    val exerciseDetailsMap = remember { mutableMapOf<Exercise, ExerciseDetails>() }

    selectedExercises.forEach { exercise ->
        if (exercise !in exerciseDetailsMap) {
            exerciseDetailsMap[exercise] = ExerciseDetails()
        }
    }

    AppScaffold(navController = navController, title = "Save routine") {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Top,
        ) {
            RoutineNameInputField(
                routineName = routineName,
                onValueChange = { routineName = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SaveRoutineButton(onClick = {
                    if (routineName.isNotEmpty()) {
                        if (selectedExercises.isEmpty()) {
                            Toast.makeText(context, "No exercises selected", Toast.LENGTH_SHORT).show()
                            return@SaveRoutineButton
                        }

                        val routineExercises = selectedExercises.map { exercise ->
                            RoutineExercise(
                                exercise = exercise,
                                sets = 3
                            )
                        }

                        val routine = Routine(
                            name = routineName,
                            exercises = routineExercises
                        )

                        RoutineRepository().createRoutine(routine) { isSuccess, message ->
                                if (isSuccess) {
                                    Toast.makeText(context, "Exercise routine has been saved", Toast.LENGTH_SHORT).show()
                                    navController.navigate(Screen.Routines.route)
                                } else {
                                    Toast.makeText(context, "SaveNewRoutine.kt RoutineRepository.kt saveRoutine", Toast.LENGTH_SHORT).show()
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                }

                            // TODO: Save routine to Firebase on the logged in user.
                        }
                    } else {
                        Toast.makeText(context, "Routine name cannot be empty", Toast.LENGTH_SHORT)
                            .show()
                    }
                })

                CancelButton(onClick = {
                    navController.popBackStack()
                })
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Selected exercises",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            selectedExercises.forEach { exercise ->
                ExerciseCard(
                    exercise = exercise,
                    exerciseDetails = exerciseDetailsMap[exercise] ?: ExerciseDetails(),
                    onDetailsChange = { updatedDetails ->
                        exerciseDetailsMap[exercise] = updatedDetails
                    }
                )
            }
        }
    }
}

@Composable
fun ExerciseCard(
    exercise: Exercise,
    exerciseDetails: ExerciseDetails,
    onDetailsChange: (ExerciseDetails) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = exercise.name,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = exercise.muscleGroup,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            var notes by remember { mutableStateOf(exerciseDetails.notes) }
            var sets by remember { mutableStateOf(exerciseDetails.sets.toString()) }
            var kg by remember { mutableStateOf(exerciseDetails.kg.toString()) }
            var reps by remember { mutableStateOf(exerciseDetails.reps.toString()) }

            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Add notes here...") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SetKgRepInputField("Sets", sets.toInt()) { sets = it.toString() }
                SetKgRepInputField("Kg", kg.toInt()) { kg = it.toString() }
                SetKgRepInputField("Reps", reps.toInt()) { reps = it.toString() }
            }

            onDetailsChange(
                exerciseDetails.copy(
                    notes = notes,
                    sets = sets.toIntOrNull() ?: 1,
                    kg = kg.toIntOrNull() ?: 0,
                    reps = reps.toIntOrNull() ?: 10
                )
            )
        }
    }
}

@Composable
fun SetKgRepInputField(
    label: String,
    value: Int,
    onValueChange: (Int) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(
            value = value.toString(),
            onValueChange = { newValue ->
                onValueChange(newValue.toIntOrNull() ?: 0)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,
            modifier = Modifier.width(60.dp)
        )
    }
}

data class ExerciseDetails(
    var sets: Int = 1,
    var kg: Int = 0,
    var reps: Int = 10,
    var notes: String = ""
)

@Composable
fun RoutineNameInputField(
    routineName: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = routineName,
        onValueChange = onValueChange,
        label = { Text("Enter routine name...") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
        ),
        modifier = Modifier.fillMaxWidth()
    )
}


@Composable
fun SaveRoutineButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(48.dp)
    ) {
        Text(
            text = "Save routine",
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun CancelButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(48.dp)
    ) {
        Text(
            text = "Cancel",
            style = MaterialTheme.typography.titleMedium
        )
    }
}