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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material3.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    navController: NavController, sharedViewModel: SharedViewModel
) {
    var routineName by remember { mutableStateOf("") }
    val context = LocalContext.current
    val selectedExercises = sharedViewModel.selectedExercises.value
    val exerciseDetailsMap = remember { mutableMapOf<Exercise, RoutineExercise>() }

    selectedExercises.forEach { exercise ->
        if (exercise !in exerciseDetailsMap) {
            exerciseDetailsMap[exercise] = RoutineExercise(exercise = exercise)
        }
    }

    AppScaffold(navController = navController, title = "Save routine") {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Top,
        ) {
            RoutineNameInputField(routineName = routineName, onValueChange = { routineName = it })

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SaveRoutineButton(onClick = {
                    if (routineName.isNotEmpty()) {
                        if (selectedExercises.isEmpty()) {
                            Toast.makeText(context, "No exercises selected", Toast.LENGTH_SHORT)
                                .show()
                            return@SaveRoutineButton
                        }

                        val routineExercises = selectedExercises.mapNotNull { exercise ->
                            exerciseDetailsMap[exercise]?.let { routineExercise ->
                                RoutineExercise(
                                    exercise = routineExercise.exercise,
                                    sets = routineExercise.sets,
                                    note = routineExercise.note
                                )
                            }
                        }

                        val routine = Routine(
                            name = routineName, exercises = routineExercises
                        )

                        RoutineRepository().createRoutine(routine) { isSuccess, message ->
                            if (isSuccess) {
                                Toast.makeText(
                                    context, "Exercise routine has been saved", Toast.LENGTH_SHORT
                                ).show()
                                navController.navigate(Screen.Routines.route)
                            } else {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(
                            context, "Routine name cannot be empty", Toast.LENGTH_SHORT
                        ).show()
                    }
                })

                CancelButton(onClick = {
                    navController.popBackStack()
                })
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Selected exercises", style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(selectedExercises) { exercise ->
                    ExerciseCard(routineExercise = exerciseDetailsMap[exercise] ?: RoutineExercise(
                        exercise = exercise
                    ), onDetailsChange = { updatedDetails ->
                        exerciseDetailsMap[exercise] = updatedDetails
                    })
                }
            }
        }
    }
}

@Composable
fun ExerciseCard(
    routineExercise: RoutineExercise, onDetailsChange: (RoutineExercise) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = routineExercise.exercise.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = routineExercise.exercise.muscleGroup,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            var notes by remember { mutableStateOf(routineExercise.note) }
            var sets by remember { mutableStateOf(routineExercise.sets.toString()) }

            NoteInputField(
                note = notes, onNoteChange = { noteValue ->
                    notes = noteValue
                    onDetailsChange(routineExercise.copy(note = noteValue, sets = sets.toInt()))
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            SetsInputField(sets.toInt()) { setsValue ->
                sets = setsValue.toString()
                onDetailsChange(routineExercise.copy(sets = setsValue, note = notes))
            }
        }
    }
}

@Composable
fun NumberSlider(
    value: Float, onValueChange: (Float) -> Unit, range: ClosedFloatingPointRange<Float> = 1f..10f
) {
    Slider(
        value = value,
        onValueChange = onValueChange,
        valueRange = range,
        steps = (range.endInclusive - range.start).toInt() - 1
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteInputField(
    note: String, onNoteChange: (String) -> Unit, modifier: Modifier = Modifier
) {
    TextField(value = note,
        onValueChange = onNoteChange,
        label = { Text("Notes") },
        placeholder = { Text("Optional") },
        singleLine = false,
        maxLines = 3, // Adjust based on your needs
        textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent, // Removes the TextField's background
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.onSurface,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = ContentAlpha.disabled),
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp) // Further reduce the vertical size if needed
    )
}

@Composable
fun SetsInputField(
    sets: Int, onValueChange: (Int) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val sliderRange = 1f..10f

        Text(
            text = if (sets > 1) "$sets Sets" else "$sets Set",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )

        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
        ) {
            // Min label
            Text(
                text = sliderRange.start.toInt().toString(),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
            )

            Spacer(modifier = Modifier.width(8.dp))

            Slider(
                value = sets.toFloat(),
                onValueChange = {
                    onValueChange(it.toInt())
                },
                valueRange = 1f..10f,
                steps = (sliderRange.endInclusive - sliderRange.start).toInt() - 1,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Max label
            Text(
                text = sliderRange.endInclusive.toInt().toString(),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
            )
        }
    }
}

@Composable
fun RoutineNameInputField(
    routineName: String, onValueChange: (String) -> Unit
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
        onClick = onClick, modifier = Modifier.height(48.dp)
    ) {
        Text(
            text = "Save routine", style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun CancelButton(onClick: () -> Unit) {
    Button(
        onClick = onClick, modifier = Modifier.height(48.dp)
    ) {
        Text(
            text = "Cancel", style = MaterialTheme.typography.titleMedium
        )
    }
}