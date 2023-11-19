package hiof.gruppe15.treningsappen.ui.component.routines

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.navigation.NavController
import hiof.gruppe15.treningsappen.data.Datasource
import hiof.gruppe15.treningsappen.model.Exercise
import hiof.gruppe15.treningsappen.ui.component.navigation.AppScaffold
import hiof.gruppe15.treningsappen.ui.component.navigation.Screen
import hiof.gruppe15.treningsappen.viewmodel.SharedViewModel
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun CreateRoutineScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel
) {
    val context = LocalContext.current
    val loadedExercises = Datasource().loadExercisesFromJson(context)
    var searchText by remember { mutableStateOf(TextFieldValue()) }
    val filteredExercises = loadedExercises.filter {
        it.name.contains(searchText.text, true)
    }
    val selectedExercises = remember { mutableStateOf(setOf<Exercise>()) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    AppScaffold(navController = navController, title = "SaveRoutine") {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(snackbarData = data)
            }

            Text(
                text = "Search and select exercises",
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                TextField(value = searchText,
                    onValueChange = { searchText = it },
                    label = { Text("Search Exercise type...") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                )
                FloatingActionButton(
                    onClick = {
                        if (selectedExercises.value.isNotEmpty()) {
                            sharedViewModel.setSelectedExercises(selectedExercises.value.toList())
                            navController.navigate(Screen.SaveNewRoutine.route)
                        } else {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Select the exercises", actionLabel = "Dismiss"
                                )
                            }
                        }
                    }, modifier = Modifier.padding(start = 8.dp)
                ) {
                    Icon(Icons.Default.Check, contentDescription = "Save")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            if (searchText.text.isNotEmpty()) {
                ExercisesWithCheckboxList(exercises = filteredExercises,
                    selectedExercises = selectedExercises.value,
                    onExerciseCheckedChange = { exercise, isChecked ->

                        selectedExercises.value = if (isChecked) {
                            selectedExercises.value + exercise
                        } else {
                            selectedExercises.value - exercise
                        }
                    }
                )
            } else {
                ExercisesWithCheckboxList(exercises = loadedExercises,
                    selectedExercises = selectedExercises.value,
                    onExerciseCheckedChange = { exercise, isChecked ->

                        selectedExercises.value = if (isChecked) {
                            selectedExercises.value + exercise
                        } else {
                            selectedExercises.value - exercise
                        }
                    }
                )
            }
        }
    }
}


@Composable
fun ExercisesWithCheckboxList(
    exercises: List<Exercise>,
    selectedExercises: Set<Exercise>,
    onExerciseCheckedChange: (Exercise, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(exercises) { exercise ->
            val isChecked = selectedExercises.contains(exercise)
            ExerciseNameWithCheckbox(
                exercise = exercise,
                isChecked = isChecked,
                onCheckedChange = { shouldCheck ->
                    onExerciseCheckedChange(exercise, shouldCheck)
                })
        }
    }
}

@Composable
fun ExerciseNameWithCheckbox(
    exercise: Exercise,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = exercise.name, style = MaterialTheme.typography.titleLarge)
        Checkbox(checked = isChecked, onCheckedChange = { checked ->
            onCheckedChange(checked)
        })
    }
}

@Composable
fun ExerciseList(exercises: List<Exercise>, modifier: Modifier = Modifier) {
    LazyColumn(userScrollEnabled = true, modifier = modifier) {
        items(exercises) { exercise ->
            hiof.gruppe15.treningsappen.ui.component.home.ExerciseCard(exercise = exercise)
        }
    }
}

@Composable
fun ExerciseCard(exercise: Exercise, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = exercise.name,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = exercise.description.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                },
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = exercise.muscleGroup.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.ROOT
                        ) else it.toString()
                    },
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.secondary
                )

                DifficultyIndicator(difficulty = exercise.difficulty)
            }
        }
    }
}

@Composable
fun DifficultyIndicator(difficulty: String) {
    val dashLength = 20.dp
    val dashWidth = 6.dp
    val dashSpacing = 4.dp

    val easyColor = Color(0xFF71CEAC)
    val mediumColor = Color(0xFF6A7FDB)
    val hardColor = Color(0xFFD32F2F)

    val dashColor = when (difficulty) {
        "Easy" -> easyColor
        "Medium" -> mediumColor
        "Hard" -> hardColor
        else -> MaterialTheme.colorScheme.onSurface // Default color if none matches
    }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Difficulty:", style = MaterialTheme.typography.bodySmall
        )

        Canvas(
            modifier = Modifier.size(
                (3 * (dashLength + dashSpacing) - dashSpacing).value.dp, dashWidth
            )
        ) {
            val difficultyNumber = difficulty.replace("Easy", "1")
                .replace("Medium", "2")
                .replace("Hard", "3")

            for (i in 0 until difficultyNumber.toInt()) {
                val startX = i * (dashLength.value + dashSpacing.value)
                drawLine(
                    color = dashColor,
                    start = Offset(startX, size.height / 2),
                    end = Offset(startX + dashLength.value, size.height / 2),
                    strokeWidth = dashWidth.value
                )
            }
        }
    }
}
