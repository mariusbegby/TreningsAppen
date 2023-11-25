package hiof.gruppe15.treningsappen.model

import java.util.UUID

data class WorkoutSession(
    val id: String = UUID.randomUUID().toString(),
    val routine: Routine = Routine(),
    val exercises: List<WorkoutSessionExercise> = routine.exercises.map { WorkoutSessionExercise(it) },
    val startTime: Long = System.currentTimeMillis(),
    val endTime: Long = System.currentTimeMillis()
)

data class WorkoutSessionExercise(
    val routineExercise: RoutineExercise = RoutineExercise(),
    val setLogs: MutableList<SetLog> = MutableList(routineExercise.sets) { SetLog() }
) {
    data class SetLog(
        var weight: String = "",
        var reps: String = "",
        var completed: Boolean = false
    )
}