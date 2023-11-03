package hiof.gruppe15.treningsappen.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.navigation.NavController
import com.example.treningsappen.R
import hiof.gruppe15.treningsappen.data.Datasource
import hiof.gruppe15.treningsappen.model.Exercise
import java.util.Locale


@Composable
fun WorkOutPlan(navController: NavController) {
    AppTopBar(navController)
    WorkoutApp(navController)
}
@Composable
fun WorkoutApp(navController: NavController) {
    val context = LocalContext.current
    val exercises = try{
        Datasource().loadExercisesFromJson(context)
    }
    catch (e: Exception) {
        // Handle the exception appropriately. For simplicity, we're just returning an empty list.
        listOf<Exercise>()
    }
    Scaffold(
        topBar = { AppTopBar(navController) },
        bottomBar = { AppBottomBar(navController) }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            ExerciseList(exercises = exercises)
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(navController: NavController){
    TopAppBar(

        title = { Text("Work Out Plan") },

        )
}
@Composable
fun AppBottomBar(navController: NavController) {
    //val context = LocalContext.current

    BottomAppBar(
        containerColor = Color.Unspecified,
        modifier = Modifier.background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .background(Color.Black, shape = CircleShape)
                    .padding(1.dp)
            ){
                // Left Home Icon with Black Border Box
                IconButton(onClick = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home Icon",
                        tint = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.width(12.dp))

            // Barbell/Dumbell Icon
            IconButton(onClick = {
                navController.navigate(Screen.WorkOutPlan.route) {
                    popUpTo(Screen.WorkOutPlan.route) { inclusive = true }
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.fitness),
                    contentDescription = "Barbell Dumbell Icon",
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            // Arrow Chart Increase Icon
            IconButton(onClick = {
                navController.navigate(Screen.Analytics.route) {
                    popUpTo(Screen.Analytics.route) { inclusive = true }
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.trendingup),
                    contentDescription = "Arrow Chart Increase Icon",
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            // Right Person Icon
            IconButton(onClick = {
                navController.navigate(Screen.Profile.route) {
                    popUpTo(Screen.Profile.route) { inclusive = true }
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Person Icon",
                    tint = Color.Black
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
        modifier = modifier.padding(8.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = exercise.name, style = MaterialTheme.typography.titleLarge)
        Checkbox(
            checked = isChecked,
            onCheckedChange = { checked ->
                onCheckedChange(checked)
            }
        )
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
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
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

    val easyColor = Color(0xFF71CEAC) // Example: greenish color
    val mediumColor = Color(0xFF6A7FDB) // Example: blueish color
    val hardColor = Color(0xFFD32F2F) // Example: reddish color

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
            text = "Difficulty:",
            style = MaterialTheme.typography.bodySmall
        )

        Canvas(
            modifier = Modifier.size(
                (3 * (dashLength + dashSpacing) - dashSpacing).value.dp,
                dashWidth
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
