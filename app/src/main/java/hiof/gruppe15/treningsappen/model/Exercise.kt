package hiof.gruppe15.treningsappen.model

data class Exercise(
    val name: String,
    val description: String,
    val steps: List<String>? = null,
    val difficulty: String,
    val muscleGroup: String,
    var selected: Boolean = false,
    val imageUrl: String? = null,
) {
}
