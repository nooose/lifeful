package lifeful.member

import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import lifeful.shared.model.BaseEntity
import java.time.Instant

@Entity
class MemberDetail(
    val bio: String = "",
    val profile: String = "",
    val registeredAt: Instant = Instant.now(),
    var activatedAt: Instant = registeredAt,
    var deactivatedAt: Instant? = null,
) : BaseEntity() {
    @JoinColumn(name = "member_id")
    @OneToOne
    lateinit var member: Member

    fun activate() {
        this.activatedAt = Instant.now()
    }

    fun deactivate() {
        this.activatedAt = Instant.now()
    }
}
