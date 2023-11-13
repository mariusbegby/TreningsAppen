package hiof.gruppe15.treningsappen.ui.component.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object ForgotPassword : Screen("forgotPassword")
    object Home : Screen("home")
    object Profile : Screen("profile")
    object Settings : Screen(route = "setting")
    object WorkOutPlan : Screen(route = "workOutPlan")
    object WorkOutSession : Screen(route = "workOutSession")
    object Analytics : Screen(route = "analytics")
    object Register : Screen("register")
    object SaveTrainingRoutine : Screen("savetrainingroutine")
}
