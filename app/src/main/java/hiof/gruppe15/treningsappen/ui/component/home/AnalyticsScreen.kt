package hiof.gruppe15.treningsappen.ui.component.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.treningsappen.R
import hiof.gruppe15.treningsappen.ui.component.navigation.Screen

@Composable
fun AnalyticsScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBarAnalytic(navController)
        Spacer(modifier = Modifier.weight(1f))
        BottomBarAnalytic(navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarAnalytic(navController: NavController) {
    TopAppBar(

        title = { Text("Analytic Page") },

        )
}

@Composable
fun BottomBarAnalytic(navController: NavController) {

    BottomAppBar(
        containerColor = Color.Unspecified, modifier = Modifier.background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .background(Color.Black, shape = CircleShape)
                    .padding(1.dp)
            ) {
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
