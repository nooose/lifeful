package readful.core.application.club.command

import org.springframework.stereotype.Service
import readful.core.domain.club.Club
import readful.core.domain.club.ClubRepository

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
}