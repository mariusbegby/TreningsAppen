package hiof.gruppe15.treningsappen.model

data class Routine(
    val id: String = "",
    val name: String = "",
    val exercises: List<Exercise> = emptyList()
) {
}
