package lifeful.health.workout

import jakarta.persistence.Column
import jakarta.persistence.Entity
import lifeful.shared.model.BaseEntity

@Entity
class SetRecord(
    val weight: Double,
    val repetitions: Int,
    @Column(name = "set_order")
    val order: Int,
) : BaseEntity() {
    init {
        require(weight > 0) { "중량은 0보다 커야 합니다." }
        require(repetitions > 0) { "반복 횟수는 0보다 커야 합니다." }
    }
}
