package readful.core.domain.book

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import readful.core.domain.shared.BaseEntity
import readful.core.domain.shared.BookChapterId

/**
 * 책 목차 도메인 모델
 * @author hd15807@gmail.com
 */
@Entity
class BookChapter(
    val title: String,
    val description: String,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: BookChapterId = BookChapterId(),
) : BaseEntity() {
}
