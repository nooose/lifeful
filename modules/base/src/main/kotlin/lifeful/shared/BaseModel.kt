package lifeful.shared

import java.time.Instant

interface BaseModel {
    val createdAt: Instant
    var modifiedAt: Instant
}
