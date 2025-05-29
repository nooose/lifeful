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
        val club = clubRepository.findById(clubId)
            ?: throw IllegalStateException("클럽을 찾을 수 없습니다.")
        club.requestJoin(memberId)
    }
}