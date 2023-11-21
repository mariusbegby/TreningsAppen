package hiof.gruppe15.treningsappen.ui.component.routines

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hiof.gruppe15.treningsappen.model.Routine
import hiof.gruppe15.treningsappen.ui.component.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutineCard(navController: NavController, routine: Routine) {
    Card(
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.elevatedCardColors(),
        onClick = {
            navController.navigate(Screen.RoutineDetails.createRoute(routine.id))
        }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = routine.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Has ${routine.exercises.count()} exercises.",
                style = MaterialTheme.typography.bodyMedium
                    .copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
        }
    }
}