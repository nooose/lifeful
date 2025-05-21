package readful.core.domain.club

import jakarta.persistence.*
import readful.core.domain.book.Book
import readful.core.domain.book.BookChapter
import readful.core.domain.shared.BaseEntity
import readful.core.domain.shared.ClubId
import java.time.LocalDateTime

@Entity
class Club(
    val title: String,
    val description: String,
    val memberCount: Int,
    val startAt: LocalDateTime,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : ClubId = ClubId()
): BaseEntity() {
    init {
        require( memberCount > 0 ) {"정원은 1명 이상이여야 합니다."}
        require(startAt.isAfter(LocalDateTime.now())) {"시작일은 현재보다 미래여야 합니다."}
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    lateinit var book : Book

    fun assignBook(book: Book){
        this.book = book;
    }

    companion object {
        fun create(
            title: String,
            description: String,
            memberCount: Int,
            startAt: LocalDateTime,
            book: Book
        ): Club {
            return Club(title, description, memberCount, startAt).apply {
                assignBook(book)
            }
        }
    }
}