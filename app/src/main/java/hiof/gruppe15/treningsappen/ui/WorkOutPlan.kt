package hiof.gruppe15.treningsappen.ui

import android.widget.Toast
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
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
import androidx.navigation.compose.rememberNavController
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
    val exercises = Datasource().loadExercisesFromJson(context)
    val navController = rememberNavController()


    Scaffold(
        topBar = { AppTopBar(navController) },
        floatingActionButton = { AppFloatingActionButton() },
        floatingActionButtonPosition = FabPosition.End,
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
    val context = LocalContext.current

    BottomAppBar(
        containerColor = Color.Unspecified,
        modifier = Modifier.background(Color.White)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
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
                    navController.navigate("home")
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
                navController.navigate("workoutplan")
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
                navController.navigate("Analytics")
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
                navController.navigate("Profile")
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
fun AppFloatingActionButton() {
    val context = LocalContext.current

    FloatingActionButton(
        onClick = {

            Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
        }
    ) {
        Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.Blue)
    }
}

@Composable
fun ExerciseList(exercises: List<Exercise>, modifier: Modifier = Modifier) {
    LazyColumn(userScrollEnabled = true,
        modifier = modifier) {
        items(exercises.size) { index ->
            ExerciseCard(exercise = exercises[index])
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
    val dashColor = when (difficulty.lowercase(Locale.ROOT)) {
        "easy" -> Color.hsl(111f, 1f, 0.4f)
        "medium" -> Color.hsl(210f, 1f, 0.4f)
        "hard" -> Color.hsl(0f, 1f, 0.4f)
        else -> MaterialTheme.colorScheme.onSurface // Default color if none matches
    }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = "Difficulty:",
            style = MaterialTheme.typography.bodySmall
        )

        Canvas(
            modifier = Modifier.size((3 * (dashLength + dashSpacing) - dashSpacing).value.dp, dashWidth)
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

