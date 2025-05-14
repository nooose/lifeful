package readful.core.domain.book

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import readful.core.domain.shared.BookId

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
    chapters: List<BookChapter> = emptyList(),
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: BookId = BookId(),
) {
    init {
        require(chapters.isNotEmpty()) { "책 목차는 1개 이상이어야 합니다." }
    }

    @JoinColumn(name = "book_id", nullable = false, updatable = false)
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    val chapters: MutableList<BookChapter> = chapters.toMutableList()
}
