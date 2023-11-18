package hiof.gruppe15.treningsappen.ui.component.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import hiof.gruppe15.treningsappen.ui.component.login.ForgotPasswordScreen
import hiof.gruppe15.treningsappen.ui.component.login.LoginScreen
import hiof.gruppe15.treningsappen.ui.component.login.RegisterScreen
import hiof.gruppe15.treningsappen.ui.component.analytics.AnalyticsScreen
import hiof.gruppe15.treningsappen.ui.component.home.HomeScreen
import hiof.gruppe15.treningsappen.ui.component.home.SaveRoutineScreen
import hiof.gruppe15.treningsappen.ui.component.profile.ProfileScreen
import hiof.gruppe15.treningsappen.ui.component.routines.RoutineScreen
import hiof.gruppe15.treningsappen.ui.component.routines.WorkoutSessionScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object ForgotPassword : Screen("forgotPassword")
    object Home : Screen("home")
    object Routines : Screen(route = "workoutPlan")
    object WorkoutSession : Screen(route = "workoutSession")
    object CreateNewRoutine : Screen("createNewRoutine")
    object Analytics : Screen(route = "analytics")
    object Profile : Screen("profile")
}

sealed class ScreenCategory(val routes: List<String>) {
    object Home : ScreenCategory(listOf("home"))
    object Workout : ScreenCategory(listOf("workoutPlan", "workoutSession", "createNewRoutine"))
    object Analytics : ScreenCategory(listOf("analytics"))
    object Profile : ScreenCategory(listOf("profile"))
}

@Composable
fun NavGraph(
    navController: NavHostController, startDestination: String = Screen.Login.route
) {
    NavHost(
        navController = navController, startDestination = startDestination
    ) {
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.Register.route) { RegisterScreen(navController) }
        composable(Screen.ForgotPassword.route) { ForgotPasswordScreen(navController) }

        composable(Screen.Home.route) { HomeScreen(navController) }

        composable(Screen.Routines.route) { RoutineScreen(navController) }
        composable(Screen.WorkoutSession.route) { WorkoutSessionScreen(navController) }
        composable(Screen.CreateNewRoutine.route) { SaveRoutineScreen(navController) }

        composable(Screen.Analytics.route) { AnalyticsScreen(navController) }

        composable(Screen.Profile.route) { ProfileScreen(navController) }
    }
}