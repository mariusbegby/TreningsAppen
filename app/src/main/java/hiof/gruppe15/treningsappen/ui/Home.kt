package hiof.gruppe15.treningsappen.ui


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import hiof.gruppe15.treningsappen.data.Datasource
import hiof.gruppe15.treningsappen.model.Exercise
import java.time.LocalTime

@Composable
fun HomeNav(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.weight(1f))
        AppBottomBar(navController)
    }
}
@Composable
fun Home(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.weight(1f))
        AppBottomBar(navController)
    }
    val currentTime = LocalTime.now()
    val greeting = when (currentTime.hour) {
        in 0..11 -> "Good Morning"
        in 12..16 -> "Good Afternoon"
        else -> "Good Evening"
    }

    var selectedExercises by remember { mutableStateOf(listOf<Exercise>()) }
    val context = LocalContext.current
    val loadedExercises = Datasource().loadExercisesFromJson(context)
    var searchText by remember { mutableStateOf(TextFieldValue()) }
    val filteredExercises = loadedExercises.filter {
        it.name.contains(searchText.text, true)
   }
    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {

        Column(
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Text(
                text = greeting,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = "Welcome to the home page",
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                label = { Text("Search...") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (searchText.text.isNotEmpty()) {
                Text(text = "Exercises", color = Color.Red, fontSize = 20.sp)
                ExercisesWithCheckboxList(
                    exercises = filteredExercises,
                    onExerciseCheckedChange = { exercise, isChecked ->
                        exercise.selected = isChecked
                        selectedExercises = filteredExercises.filter { it.selected }
                    }
                )
            }
        }

        FloatingActionButton(
            onClick = {  navController.navigate(Screen.SaveTrainingRoutine.route)},
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    }
}

