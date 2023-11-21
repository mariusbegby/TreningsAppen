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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hiof.gruppe15.treningsappen.data.RoutineRepository
import hiof.gruppe15.treningsappen.model.Routine
import hiof.gruppe15.treningsappen.model.RoutineExercise
import hiof.gruppe15.treningsappen.ui.component.navigation.AppScaffold
import hiof.gruppe15.treningsappen.viewmodel.SharedViewModel

@Composable
fun SaveRoutineScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel
) {
    var routineName by remember { mutableStateOf("") }
    val context = LocalContext.current
    val selectedExercises = sharedViewModel.selectedExercises.value

    AppScaffold(navController = navController, title = "SaveRoutine") {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = routineName,
                onValueChange = { routineName = it },
                label = { Text("Enter routine name...") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    if (routineName.isNotEmpty()) {
                        if (selectedExercises.isEmpty()) {
                            Toast.makeText(context, "No exercises selected", Toast.LENGTH_SHORT).show()
                            return@Button
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
                                    navController.popBackStack()
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
                }) {
                    Text("Save")
                }
                Button(onClick = {
                    navController.popBackStack()
                }) {
                    Text("Cancel")
                }
            }
        }
    }
}
