package lifeful.workout

import jakarta.persistence.*
import java.time.Instant
import lifeful.shared.BaseModel
import lifeful.shared.id.ExerciseId

@Table(name = "exercise")
@Entity
class ExerciseEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: ExerciseId,
    val name: String,
    @Enumerated(EnumType.STRING)
    val category: ExerciseCategory,
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "exercise_muscle_group",
        joinColumns = [JoinColumn(name = "exercise_id")],
    )
    val muscleGroups: Set<MuscleGroup>,
    override val createdAt: Instant = Instant.now(),
    override val modifiedAt: Instant = createdAt,
) : BaseModel {
    fun toDomain(): Exercise {
        return Exercise(
            id = id,
            name = name,
            category = category,
            muscleGroups = muscleGroups,
            createdAt = createdAt,
        )
    }

    companion object {
        fun from(exercise: Exercise): ExerciseEntity {
            return ExerciseEntity(
                id = exercise.id,
                name = exercise.name,
                category = exercise.category,
                muscleGroups = exercise.muscleGroups,
                createdAt = exercise.createdAt,
            )
        }
    }
}
