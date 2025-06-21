package lifeful.shared

import java.time.LocalDateTime

interface BaseModel {
    val createdAt: LocalDateTime
    val modifiedAt: LocalDateTime
}
