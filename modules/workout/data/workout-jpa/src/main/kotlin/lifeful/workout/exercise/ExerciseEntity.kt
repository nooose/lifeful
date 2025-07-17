package lifeful.workout.exercise

import jakarta.persistence.CollectionTable
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.Table
import java.time.Instant
import lifeful.shared.BaseModel
import lifeful.shared.id.ExerciseId

@Table(name = "exercise")
@Entity
internal class ExerciseEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
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
            id = ExerciseId(id),
            name = name,
            category = category,
            muscleGroups = muscleGroups,
            createdAt = createdAt,
        )
    }

    companion object {
        fun from(exercise: Exercise): ExerciseEntity {
            return ExerciseEntity(
                id = exercise.id.value,
                name = exercise.name,
                category = exercise.category,
                muscleGroups = exercise.muscleGroups,
                createdAt = exercise.createdAt,
            )
        }
    }
}
