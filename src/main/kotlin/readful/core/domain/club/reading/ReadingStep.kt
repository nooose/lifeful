package readful.core.domain.club.reading

import jakarta.persistence.*
import readful.core.domain.book.BookChapter
import readful.core.domain.club.Club
import readful.core.domain.shared.BaseEntity
import readful.core.domain.shared.ReadingStepId

@Entity
class ReadingStep(
    val stepOrder: Int,
    @Embedded
    val stepPeriod: StepPeriod,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: ReadingStepId = ReadingStepId()
) : BaseEntity() {

    @JoinColumn(name = "club_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    lateinit var club: Club

    @JoinTable(
        name = "reading_step_chapter",
        joinColumns = [JoinColumn(name = "reading_step_id")],
        inverseJoinColumns = [JoinColumn(name = "chapter_id")]
    )
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    val chapters: MutableList<BookChapter> = mutableListOf()

    fun assignClub(club: Club) {
        this.club = club
    }

    fun assignChapters(chapterList: List<BookChapter>) {
        chapters.clear()
        chapters.addAll(chapterList)
    }

    companion object {
        fun create(
            stepOrder: Int,
            stepPeriod: StepPeriod,
            chapters: List<BookChapter>,
        ): ReadingStep {
            return ReadingStep(
                stepOrder = stepOrder,
                stepPeriod = stepPeriod,
            ).apply {
                assignChapters(chapters)
            }
        }
    }
}