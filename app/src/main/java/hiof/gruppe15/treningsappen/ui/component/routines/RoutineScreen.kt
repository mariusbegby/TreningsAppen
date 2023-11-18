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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.navigation.NavController
import hiof.gruppe15.treningsappen.data.Datasource
import hiof.gruppe15.treningsappen.model.Exercise
import hiof.gruppe15.treningsappen.ui.component.navigation.AppScaffold
import java.util.Locale

@Composable
fun RoutineScreen(navController: NavController) {
    WorkoutApp(navController)
}

@Composable
fun WorkoutApp(navController: NavController) {
    val context = LocalContext.current
    val exercises = try {
        Datasource().loadExercisesFromJson(context)
    } catch (e: Exception) {
        listOf<Exercise>()
    }

    AppScaffold(navController = navController, title = "Routines") {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            color = MaterialTheme.colorScheme.background
        ) {
            ExerciseList(exercises = exercises)
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
            ExerciseCard(exercise = exercise)
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

    val dashColor = when (difficulty.lowercase(Locale.ROOT)) {
        "easy" -> easyColor
        "medium" -> mediumColor
        "hard" -> hardColor
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
            val numberOfDashes = when (difficulty.lowercase(Locale.ROOT)) {
                "easy" -> 1
                "medium" -> 2
                "hard" -> 3
                else -> 0
            }

            for (i in 0 until numberOfDashes) {
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
