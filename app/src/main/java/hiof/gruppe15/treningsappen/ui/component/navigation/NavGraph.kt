package hiof.gruppe15.treningsappen.ui.component.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import hiof.gruppe15.treningsappen.ui.component.analytics.AnalyticsScreen
import hiof.gruppe15.treningsappen.ui.component.home.HomeScreen
import hiof.gruppe15.treningsappen.ui.component.home.TestingScreen
import hiof.gruppe15.treningsappen.ui.component.login.ForgotPasswordScreen
import hiof.gruppe15.treningsappen.ui.component.login.LoginScreen
import hiof.gruppe15.treningsappen.ui.component.login.RegisterScreen
import hiof.gruppe15.treningsappen.ui.component.profile.ProfileScreen
import hiof.gruppe15.treningsappen.ui.component.routines.CreateRoutineScreen
import hiof.gruppe15.treningsappen.ui.component.routines.RoutineDetailsScreen
import hiof.gruppe15.treningsappen.ui.component.routines.RoutineScreen
import hiof.gruppe15.treningsappen.ui.component.routines.SaveRoutineScreen
import hiof.gruppe15.treningsappen.ui.component.routines.WorkoutSessionScreen
import hiof.gruppe15.treningsappen.viewmodel.SharedViewModel

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object ForgotPassword : Screen("forgotPassword")
    object Home : Screen("home")
    object Routines : Screen(route = "workoutPlan")
    object RoutineDetails : Screen(route = "routineDetails/{routineId}") {
        fun createRoute(routineId: String) = "routineDetails/$routineId"
    }
    object WorkoutSession : Screen(route = "workoutSession")
    object CreateNewRoutine : Screen("createNewRoutine")
    object SaveNewRoutine : Screen("saveNewRoutine")
    object Analytics : Screen(route = "analytics")
    object Profile : Screen("profile")
    object Testing : Screen("testing")
}

sealed class ScreenCategory(private val baseRoutes: List<String>) {
    object Home : ScreenCategory(listOf("home", "testing"))
    object Routines : ScreenCategory(listOf("workoutPlan", "workoutSession", "createNewRoutine", "saveNewRoutine", "routineDetails"))
    object Analytics : ScreenCategory(listOf("analytics"))
    object Profile : ScreenCategory(listOf("profile"))

    fun includes(route: String?): Boolean {
        if(route == null) return false
        return baseRoutes.any { baseRoute -> route.startsWith(baseRoute) }
    }
}

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Login.route,
    sharedViewModel: SharedViewModel
) {
    NavHost(
        navController = navController, startDestination = startDestination
    ) {
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.Register.route) { RegisterScreen(navController) }
        composable(Screen.ForgotPassword.route) { ForgotPasswordScreen(navController) }

        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Testing.route) { TestingScreen(navController) }

        composable(Screen.Routines.route) { RoutineScreen(navController, sharedViewModel.routineViewModel) }
        composable(
            route = Screen.RoutineDetails.route,
            arguments = listOf(navArgument("routineId") { type = NavType.StringType })
        ) { backStackEntry ->
            val routineId = backStackEntry.arguments?.getString("routineId")
            if (routineId != null) {
                RoutineDetailsScreen(navController, routineId, sharedViewModel.routineViewModel)
            }
        }
        composable(Screen.WorkoutSession.route) { WorkoutSessionScreen(navController) }
        composable(Screen.CreateNewRoutine.route) { CreateRoutineScreen(navController, sharedViewModel) }
        composable(Screen.SaveNewRoutine.route) { SaveRoutineScreen(navController, sharedViewModel) }

        composable(Screen.Analytics.route) { AnalyticsScreen(navController) }

        composable(Screen.Profile.route) { ProfileScreen(navController) }
    }
}