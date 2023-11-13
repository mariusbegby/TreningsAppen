package hiof.gruppe15.treningsappen.utils

object ValidationUtils {
    fun isValidEmail(target: CharSequence?): Boolean {
        return if (target == null || target.isEmpty()) {
            false
        } else {
            android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }

    fun isPasswordStrong(password: String?): Boolean {
        return password != null && password.length >= 8 && password.any { it.isDigit() } && password.any { it.isLetter() }
    }
}
