package readful.ui.web.club

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import readful.core.application.club.command.ClubCommandService
import readful.core.application.club.query.ClubQueryService
import readful.core.domain.book.BookChapter
import readful.core.domain.book.BookRepository
import readful.core.domain.club.reading.ReadingStep
import readful.core.domain.club.reading.StepPeriod
import readful.core.domain.shared.BookId
import readful.core.domain.shared.ClubId
import java.net.URI

@RequestMapping("/clubs")
@RestController
class ClubRestController(
    private val clubQueryService: ClubQueryService,
    private val bookRepository: BookRepository,
    private val clubCommandService: ClubCommandService
) {

    @PostMapping
    fun createClub(@RequestBody request: CreateClubRequest): ResponseEntity<ClubResponse> {
        val book = bookRepository.findById(BookId(request.bookId))
            ?: return ResponseEntity.badRequest().build()

        val steps = request.steps.map { step ->
            val chapters = mutableListOf<BookChapter>()

            for (chapterId in step.chapterIds) {
                val chapter = book.chapters.find { chapter -> chapter.id.value == chapterId }
                if (chapter != null) {
                    chapters.add(chapter)
                }
            }

            ReadingStep.create(
                stepOrder = step.stepOrder,
                stepPeriod = StepPeriod(step.startDate, step.endDate),
                chapters = chapters
            )
        }

        val club = clubCommandService.createClub(
            title = request.title,
            description = request.description,
            memberCount = request.memberCount,
            startAt = request.startAt,
            book = book,
            steps = steps
        )

        return ResponseEntity
            .created(URI.create("/clubs/${club.id.value}"))
            .body(ClubResponse.from(club))
    }

    @GetMapping("/{id}")
    fun getClub(@PathVariable id: Long): ResponseEntity<ClubResponse> {
        return try {
            val club = clubQueryService.getClub(ClubId(id))
            return ResponseEntity.ok(ClubResponse.from(club))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping
    fun getAllClub(): ResponseEntity<List<ClubResponse>> {
        val clubs = clubQueryService.getAllClubs()
            .map { club -> ClubResponse.from(club) }

        return ResponseEntity.ok(clubs)
    }
}