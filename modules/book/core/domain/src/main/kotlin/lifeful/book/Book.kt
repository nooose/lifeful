package lifeful.book

import java.time.LocalDateTime
import lifeful.shared.BaseModel
import lifeful.shared.BookId

/**
 * 책 도메인 모델
 * @author hd15807@gmail.com
 */
data class Book(
    val isbn: String,
    val title: String,
    val description: String,
    val author: String,
    val publisher: String,
    val id: BookId = BookId(),
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override val modifiedAt: LocalDateTime = LocalDateTime.now(),
) : BaseModel
