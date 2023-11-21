package hiof.gruppe15.treningsappen.ui.component.routines

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hiof.gruppe15.treningsappen.data.Datasource
import hiof.gruppe15.treningsappen.model.Exercise
import hiof.gruppe15.treningsappen.ui.component.navigation.AppScaffold
import hiof.gruppe15.treningsappen.ui.component.navigation.Screen
import hiof.gruppe15.treningsappen.viewmodel.SharedViewModel
import kotlinx.coroutines.launch

@Composable
fun CreateRoutineScreen(
    navController: NavController, sharedViewModel: SharedViewModel
) {
    val context = LocalContext.current
    val loadedExercises = Datasource().loadExercisesFromJson(context)
    var searchText by remember { mutableStateOf(String()) }
    val filteredExercises = loadedExercises.filter {
        it.name.contains(searchText, true)
    }
    val selectedExercises = remember { mutableStateOf(setOf<Exercise>()) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AppScaffold(navController = navController, title = "Create new routine") {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {

                Description()

                Spacer(modifier = Modifier.padding(8.dp))

                ExerciseSearchInputField(exerciseName = searchText, onNext = {
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
                }, onValueChange = { searchText = it })

                Spacer(modifier = Modifier.height(16.dp))

                if (searchText.isNotEmpty()) {
                    ExercisesWithCheckboxList(exercises = filteredExercises,
                        selectedExercises = selectedExercises.value,
                        onExerciseCheckedChange = { exercise, isChecked ->

                            selectedExercises.value = if (isChecked) {
                                selectedExercises.value + exercise
                            } else {
                                selectedExercises.value - exercise
                            }
                        })
                } else {
                    ExercisesWithCheckboxList(exercises = loadedExercises,
                        selectedExercises = selectedExercises.value,
                        onExerciseCheckedChange = { exercise, isChecked ->

                            selectedExercises.value = if (isChecked) {
                                selectedExercises.value + exercise
                            } else {
                                selectedExercises.value - exercise
                            }
                        })
                }
            }

            FloatingCreateRoutineButton(onClick = {
                if (selectedExercises.value.isNotEmpty()) {
                    sharedViewModel.setSelectedExercises(selectedExercises.value.toList())
                    navController.navigate(Screen.SaveNewRoutine.route)
                } else {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = "You must select at least one exercise",
                            actionLabel = "Dismiss"
                        )
                    }
                }
            })

            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 56.dp)
            ) { data ->
                Snackbar(snackbarData = data)
            }
        }
    }
}

@Composable
private fun Description() {
    Text(
        text = "Search and select exercises to add to your routine.",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
fun ExerciseSearchInputField(
    exerciseName: String, onNext: (KeyboardActionScope.() -> Unit), onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = exerciseName,
        onValueChange = onValueChange,
        label = { Text("Search exercise name...") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(onNext = onNext),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun FloatingCreateRoutineButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        FloatingActionButton(
            onClick = onClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 96.dp, end = 16.dp)
        ) {
            Icon(Icons.Default.Create, contentDescription = "Save")
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
                }
            )
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
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = exercise.name,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
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
