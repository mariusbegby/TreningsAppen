package hiof.gruppe15.treningsappen.model

data class WorkoutSession(
    val routine: Routine,
    val exercises: List<WorkoutSessionExercise> = routine.exercises.map { WorkoutSessionExercise(it) }
)

data class WorkoutSessionExercise(
    val routineExercise: RoutineExercise,
    val setLogs: MutableList<SetLog> = MutableList(routineExercise.sets) { SetLog() }
) {
    data class SetLog(var weight: String = "", var reps: String = "")
}