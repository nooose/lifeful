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
import readful.core.domain.book.BookRepository
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

        val club = clubCommandService.createClub(
            title = request.title,
            description = request.description,
            memberCount = request.memberCount,
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

    @PostMapping("/{clubId}/members/join")
    fun requestJoin(
        @PathVariable clubId: Long,
        @RequestBody request: MemberRequest
    ): ResponseEntity<Void> {
        clubCommandService.requestJoin(ClubId(clubId), request.memberId)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/{clubId}/members/{memberId}/accept")
    fun acceptMember(
        @PathVariable clubId: Long,
        @PathVariable memberId: Int,
        @RequestBody request: HostRequest
    ): ResponseEntity<Void> {
        clubCommandService.acceptMember(ClubId(clubId), memberId, request.hostId)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/{clubId}/members/{memberId}/reject")
    fun rejectMember(
        @PathVariable clubId: Long,
        @PathVariable memberId: Int,
        @RequestBody request: HostRequest
    ): ResponseEntity<Void> {
        clubCommandService.rejectMember(ClubId(clubId), memberId, request.hostId)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/{clubId}/members/{memberId}/leave")
    fun leaveClub(
        @PathVariable clubId: Long,
        @PathVariable memberId: Int,
    ): ResponseEntity<Void> {
        clubCommandService.leaveClub(ClubId(clubId), memberId)
        return ResponseEntity.ok().build()
    }

}