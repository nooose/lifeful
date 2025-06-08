package lifefule.book.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import lifefule.shared.BaseEntity
import lifefule.shared.BookId

/**
 * 책 도메인 모델
 * @author hd15807@gmail.com
 */
@Entity
class Book(
    val isbn: String,
    val title: String,
    val description: String,
    val author: String,
    val publisher: String,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: BookId = BookId(),
) : BaseEntity()
