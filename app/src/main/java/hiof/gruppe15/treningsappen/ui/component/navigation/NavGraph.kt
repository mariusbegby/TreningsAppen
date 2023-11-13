package hiof.gruppe15.treningsappen.ui.component.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import hiof.gruppe15.treningsappen.ui.component.auth.ForgotPasswordScreen
import hiof.gruppe15.treningsappen.ui.component.auth.LoginScreen
import hiof.gruppe15.treningsappen.ui.component.auth.RegisterScreen
import hiof.gruppe15.treningsappen.ui.component.home.AnalyticsScreen
import hiof.gruppe15.treningsappen.ui.component.home.HomeScreen
import hiof.gruppe15.treningsappen.ui.component.home.SaveRoutineScreen
import hiof.gruppe15.treningsappen.ui.component.profile.ProfileScreen
import hiof.gruppe15.treningsappen.ui.component.settings.SettingsScreen
import hiof.gruppe15.treningsappen.ui.component.workout.RoutineScreen
import hiof.gruppe15.treningsappen.ui.component.workout.WorkoutSessionScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object ForgotPassword : Screen("forgotPassword")
    object Home : Screen("home")
    object Profile : Screen("profile")
    object Settings : Screen(route = "settings")
    object WorkoutPlan : Screen(route = "workoutPlan")
    object WorkoutSession : Screen(route = "workoutSession")
    object Analytics : Screen(route = "analytics")
    object SaveTrainingRoutine : Screen("saveTrainingRoutine")
}

@Composable
fun NavGraph(
    navController: NavHostController, startDestination: String = Screen.Login.route
) {
    NavHost(
        navController = navController, startDestination = startDestination
    ) {
        composable(Screen.Register.route) { RegisterScreen(navController) }
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Profile.route) { ProfileScreen(navController) }
        composable(Screen.Settings.route) { SettingsScreen(navController) }
        composable(Screen.WorkoutPlan.route) { RoutineScreen(navController) }
        composable(Screen.WorkoutSession.route) { WorkoutSessionScreen(navController) }
        composable(Screen.Analytics.route) { AnalyticsScreen(navController) }
        composable(Screen.ForgotPassword.route) { ForgotPasswordScreen(navController) }
        composable(Screen.SaveTrainingRoutine.route) { SaveRoutineScreen(navController) }
    }
}