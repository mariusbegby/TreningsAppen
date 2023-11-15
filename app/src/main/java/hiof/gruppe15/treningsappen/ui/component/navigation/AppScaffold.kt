package hiof.gruppe15.treningsappen.ui.component.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.treningsappen.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    navController: NavController,
    title: String,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(title) }, navigationIcon = {
                IconButton(onClick = {
                    navController.navigateUp()
                }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Go back")
                }
            })
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.Unspecified, modifier = Modifier.background(Color.White)
            ) {
                BottomNavigationRow(navController)
            }
        }
    ) { innerPadding ->
        content(PaddingValues(
            top = innerPadding.calculateTopPadding(),
            bottom = innerPadding.calculateBottomPadding(),
            start = 16.dp,
            end = 16.dp
        ))
    }
}

@Composable
private fun BottomNavigationRow(navController: NavController) {
    val currentRoute = getCurrentRoute(navController)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        HomeNavButton(navController, isSelected = isRouteSelected(currentRoute, ScreenCategory.Home))
        Spacer(modifier = Modifier.width(12.dp))

        WorkoutPlanNavButton(navController, isSelected = isRouteSelected(currentRoute, ScreenCategory.Workout))
        Spacer(modifier = Modifier.width(12.dp))

        AnalyticsNavButton(navController, isSelected = isRouteSelected(currentRoute, ScreenCategory.Analytics))
        Spacer(modifier = Modifier.width(12.dp))

        ProfileNavButton(navController, isSelected = isRouteSelected(currentRoute, ScreenCategory.Profile))
    }
}

@Composable
private fun HomeNavButton(navController: NavController, isSelected: Boolean) {
    NavButton(
        navController = navController,
        isSelected = isSelected,
        iconWrapper = IconWrapper.ImageVectorIcon(Icons.Default.Home),
        contentDescription = "Home Icon",
        route = Screen.Home.route
    )
}

@Composable
private fun WorkoutPlanNavButton(navController: NavController, isSelected: Boolean) {
    NavButton(
        navController = navController,
        isSelected = isSelected,
        iconWrapper = IconWrapper.PainterIcon(painterResource(id = R.drawable.fitness)),
        contentDescription = "Barbell Dumbell Icon",
        route = Screen.WorkoutPlan.route
    )
}


@Composable
private fun AnalyticsNavButton(navController: NavController, isSelected: Boolean) {
    NavButton(
        navController = navController,
        isSelected = isSelected,
        iconWrapper = IconWrapper.PainterIcon(painterResource(id = R.drawable.trendingup)),
        contentDescription = "Arrow Chart Increase Icon",
        route = Screen.Analytics.route
    )
}

@Composable
private fun ProfileNavButton(navController: NavController, isSelected: Boolean) {
    NavButton(
        navController = navController,
        isSelected = isSelected,
        iconWrapper = IconWrapper.ImageVectorIcon(Icons.Default.Person),
        contentDescription = "Person Icon",
        route = Screen.Profile.route
    )
}

@Composable
private fun NavButton(
    navController: NavController,
    isSelected: Boolean,
    iconWrapper: IconWrapper,
    contentDescription: String,
    route: String
) {
    val backgroundColor = if (isSelected) Color.Black else Color.White
    val iconColor = if (isSelected) Color.White else Color.Black

    Box(
        modifier = Modifier
            .size(30.dp)
            .background(backgroundColor, shape = CircleShape)
            .padding(1.dp)
    ) {
        IconButton(onClick = {
            navController.navigate(route) {
                popUpTo(route) { inclusive = true }
            }
        }) {
            when (iconWrapper) {
                is IconWrapper.ImageVectorIcon -> Icon(
                    imageVector = iconWrapper.imageVector,
                    contentDescription = contentDescription,
                    tint = iconColor
                )
                is IconWrapper.PainterIcon -> Icon(
                    painter = iconWrapper.painter,
                    contentDescription = contentDescription,
                    tint = iconColor
                )
            }
        }
    }
}

@Composable
fun getCurrentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

fun isRouteSelected(currentRoute: String?, category: ScreenCategory): Boolean {
    return currentRoute in category.routes
}

sealed class IconWrapper {
    data class ImageVectorIcon(val imageVector: ImageVector) : IconWrapper()
    data class PainterIcon(val painter: Painter) : IconWrapper()
}