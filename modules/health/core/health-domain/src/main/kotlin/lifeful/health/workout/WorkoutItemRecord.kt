package lifeful.health.workout

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import lifeful.shared.id.ExerciseId
import lifeful.shared.model.BaseEntity

@Entity
class WorkoutItemRecord(
    val exerciseId: ExerciseId,
    sets: List<SetRecord> = emptyList(),
    @Column(name = "item_order")
    val order: Int,
) : BaseEntity() {
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_item_record_id")
    val sets: MutableList<SetRecord> = sets.toMutableList()

    fun addSet(weight: Double, repetitions: Int) {
        val order = (this.sets.maxOfOrNull { it.order } ?: 0) + 1
        this.sets.add(
            SetRecord(
                weight = weight,
                repetitions = repetitions,
                order = order,
            )
        )
    }
}
