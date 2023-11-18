package hiof.gruppe15.treningsappen.ui.component.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hiof.gruppe15.treningsappen.data.Datasource
import hiof.gruppe15.treningsappen.model.Exercise
import hiof.gruppe15.treningsappen.ui.component.navigation.AppScaffold
import hiof.gruppe15.treningsappen.ui.component.navigation.Screen
import hiof.gruppe15.treningsappen.ui.component.routines.ExercisesWithCheckboxList
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val loadedExercises = Datasource().loadExercisesFromJson(context)
    var searchText by remember { mutableStateOf(TextFieldValue()) }
    val filteredExercises = loadedExercises.filter {
        it.name.contains(searchText.text, true)
    }
    val selectedExercises = remember { mutableStateOf(setOf<Exercise>()) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    AppScaffold(navController = navController, title = "Home") {
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
                            navController.navigate(Screen.CreateNewRoutine.route)
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
