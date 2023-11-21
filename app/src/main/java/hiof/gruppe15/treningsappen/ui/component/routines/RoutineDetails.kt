package hiof.gruppe15.treningsappen.ui.component.routines

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.treningsappen.R
import hiof.gruppe15.treningsappen.model.RoutineExercise
import hiof.gruppe15.treningsappen.ui.component.navigation.AppScaffold
import hiof.gruppe15.treningsappen.viewmodel.RoutineViewModel

@Composable
fun RoutineDetailsScreen(navController: NavController, routineId: String, routineViewModel: RoutineViewModel) {
    val routine = routineViewModel.getRoutineById(routineId)

    AppScaffold(navController = navController, title = "") {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            routine?.let {
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(24.dp))

                StartRoutineButton(onClick = {
                })

                Spacer(modifier = Modifier.height(8.dp))

                DeleteRoutineButton(onClick = {
                })

                Spacer(modifier = Modifier.height(24.dp))

                routine.exercises.forEach() { exercise ->
                    RoutineExerciseCard(exercise)
                }
            } ?: Text("Routine was not found")
        }
    }
}

@Composable
fun StartRoutineButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Text(
            text = "Start routine (TODO)",
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun DeleteRoutineButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Text(
            text = "Delete routine (TODO)",
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun RoutineExerciseCard(exercise: RoutineExercise) {
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
            if (exercise.exercise.imageUrl != null) {
                Image(
                    painter = rememberAsyncImagePainter(exercise.exercise.imageUrl),
                    contentDescription = "Exercise Image",
                    modifier = Modifier
                        .size(60.dp)
                        .padding(end = 8.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Fit
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .padding(end = 8.dp)
                        .clip(RoundedCornerShape(8.dp)),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "App Logo",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = exercise.exercise.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(){
                    Text(
                        text = "${exercise.sets} sets",
                        style = MaterialTheme.typography.bodyMedium
                            .copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = exercise.exercise.muscleGroup,
                        style = MaterialTheme.typography.bodyMedium
                            .copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                if(exercise.note.isNotEmpty()) {
                    Text(
                        text = "Notes: ${exercise.note}",
                        style = MaterialTheme.typography.bodyMedium
                            .copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    )
                }
            }
        }
    }
}