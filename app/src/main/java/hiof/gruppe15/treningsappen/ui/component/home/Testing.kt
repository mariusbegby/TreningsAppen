package hiof.gruppe15.treningsappen.ui.component.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hiof.gruppe15.treningsappen.model.Exercise
import hiof.gruppe15.treningsappen.ui.component.navigation.AppScaffold
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import coil.compose.rememberAsyncImagePainter

@Composable
fun TestingScreen(navController: NavController) {
    AppScaffold(navController = navController, title = "Testing") {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val exercises = listOf(
                Exercise(
                    name = "Push-Ups",
                    description = "A basic exercise for upper body strength.",
                    steps = listOf("Lie face down on the floor", "Place your hands shoulder-width apart", "Push your body up until your arms are fully extended", "Lower your body back down"),
                    difficulty = "Medium",
                    muscleGroup = "Chest",
                    selected = false,
                    imageUrl = "https://i.imgur.com/CJrwwGH_d.webp?maxwidth=760&fidelity=grand"
                ),
                Exercise(
                    name = "Squats",
                    description = "Effective for building lower body strength.",
                    steps = listOf("Stand with feet a little wider than shoulder-width", "Bend your knees and lower your body", "Keep your back straight", "Return to standing position"),
                    difficulty = "Easy",
                    muscleGroup = "Quads",
                    selected = false,
                    imageUrl = "https://i.imgur.com/MnFaNO9_d.webp?maxwidth=760&fidelity=grand"
                ),
                Exercise(
                    name = "Deadlift",
                    description = "A compound exercise for overall strength.",
                    steps = listOf("Stand with feet hip-width apart", "Bend and grab the barbell", "Lift the barbell by straightening your legs", "Lower the barbell to the ground"),
                    difficulty = "Hard",
                    muscleGroup = "Hamstrings",
                    selected = false,
                    imageUrl = "https://i.imgur.com/Ki2cxni.png"
                )
            )

            ExerciseListScreen(exercises)
        }
    }
}

@Composable
fun ExerciseListScreen(exercises: List<Exercise>) {
    LazyColumn {
        items(exercises) { exercise ->
            ExerciseCard(exercise)
        }
    }
}

@Composable
fun ExerciseCard(exercise: Exercise) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.elevatedCardColors()
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (exercise.imageUrl != null) {
                Image(
                    painter = rememberAsyncImagePainter(exercise.imageUrl),
                    contentDescription = "Exercise Image",
                    modifier = Modifier
                        .size(60.dp)
                        .padding(end = 8.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Fit
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = exercise.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = exercise.muscleGroup,
                    style = MaterialTheme.typography.bodyMedium
                        .copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                )
            }

            Checkbox(
                checked = exercise.selected,
                onCheckedChange = { checked ->
                    exercise.selected = checked
                }
            )
        }
    }
}