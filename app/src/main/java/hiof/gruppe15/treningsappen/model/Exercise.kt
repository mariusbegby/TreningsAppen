package hiof.gruppe15.treningsappen.model

data class Exercise(
    val name: String="",
    val description: String="",
    val steps: List<String>? = null,
    val difficulty: String="",
    val muscleGroup: String="",
    var selected: Boolean = false,
    val imageUrl: String? = null,
)

data class RoutineExercise(
    val exercise: Exercise = Exercise(),
    val sets: Int = 1,
    val note: String = ""
)