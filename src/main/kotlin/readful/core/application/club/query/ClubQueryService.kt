package readful.core.application.club.query

import org.springframework.stereotype.Service
import readful.core.domain.club.Club
import readful.core.domain.club.ClubRepository
import readful.core.domain.shared.ClubId

@Service
class ClubQueryService(
    private val clubRepository: ClubRepository
) {
    fun getClub(id: ClubId): Club {
        return clubRepository.findById(id)
            ?: throw IllegalArgumentException("Club with id ${id.value} not found")
    }

    fun getAllClubs(): List<Club> {
        return clubRepository.findAll()
    }
}
