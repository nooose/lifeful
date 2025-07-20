package lifeful.shared.model

import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.util.Objects

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class BaseEntity(
    id: Long = 0L,
    createdAt: Instant = Instant.now(),
    modifiedAt: Instant = createdAt,
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = id
    val createdAt: Instant = createdAt
    @field:LastModifiedDate
    var modifiedAt: Instant = modifiedAt

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BaseEntity

        return id == other.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }

    override fun toString(): String {
        return "Entity id = $id"
    }
}
