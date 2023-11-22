package hiof.gruppe15.treningsappen.model

data class WorkoutSession (
    val routine: Routine,
    val exercises: List<WorkoutSessionExercise> = routine.exercises.map { WorkoutSessionExercise(it.exercise) }
)

data class WorkoutSessionExercise(
    val exercise: Exercise,
    val setLogs: MutableList<SetLog> = mutableListOf()
) {
    data class SetLog(var weight: String = "", var reps: String = "")
}