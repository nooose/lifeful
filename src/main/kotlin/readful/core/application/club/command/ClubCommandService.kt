package readful.core.application.club.command

import org.springframework.stereotype.Service
import readful.core.domain.club.Club
import readful.core.domain.club.ClubRepository
import readful.core.domain.shared.ClubId

@Service
class ClubCommandService(
    private val clubRepository: ClubRepository
) {

    fun createClub(
        title: String,
        description: String,
        memberCount: Int,
    ): Club {

        val club = Club(
            title = title,
            description = description,
            memberMaxCount = memberCount,
            hostId = 1,
        )

        return clubRepository.save(club)
    }

    fun requestJoin(
        clubId: ClubId,
        memberId: Int
    ) {
        val club = getClub(clubId)
        club.requestJoin(memberId)
    }

    fun acceptMember(
        clubId: ClubId,
        memberId: Int,
        hostId: Int
    ) {
        val club = getClub(clubId)
        club.acceptMember(memberId, hostId)
    }

    private fun getClub(clubId: ClubId): Club {
        return clubRepository.findById(clubId)
            ?: throw IllegalStateException("클럽을 찾을 수 없습니다.")
    }
}