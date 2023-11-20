package hiof.gruppe15.treningsappen.model

import java.util.UUID

data class Routine(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val exercises: List<RoutineExercise> = emptyList()
)