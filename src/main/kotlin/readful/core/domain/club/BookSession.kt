package readful.core.domain.club

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import readful.core.domain.book.Book

@Entity
class BookSession(
    @ManyToOne
    val book: Book,
    @ManyToOne
    val club: Club,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
) {
}