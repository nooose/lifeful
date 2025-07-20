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
import java.time.Instant
import lifeful.shared.BaseModel

@Entity
class Exercise(
    var name: String,
    @Enumerated(EnumType.STRING)
    var category: ExerciseCategory,
    muscleGroups: Set<MuscleGroup>,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    override val createdAt: Instant = Instant.now(),
    override var modifiedAt: Instant = createdAt,
) : BaseModel {
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "exercise_muscle_group",
        joinColumns = [JoinColumn(name = "exercise_id")],
    )
    val muscleGroups: MutableSet<MuscleGroup> = muscleGroups.toMutableSet()

    init {
        require(muscleGroups.isNotEmpty()) {
            MuscleGroupRequiredException("운동 부위를 하나 이상 선택해 주세요.")
        }
    }

    fun modify(
        name: String,
        category: ExerciseCategory,
        muscleGroups: Set<MuscleGroup>,
    ) {
        this.muscleGroups.addAll(muscleGroups)
        this.name = name
        this.category = category
    }
}
