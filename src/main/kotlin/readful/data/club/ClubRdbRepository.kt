package readful.data.club

import org.springframework.stereotype.Repository
import readful.core.domain.club.Club
import readful.core.domain.club.ClubRepository
import readful.core.domain.shared.ClubId

@Repository
class ClubRdbRepository(
    private val clubJpaRepository: ClubJpaRepository
) : ClubRepository {
    override fun findAll(): List<Club> {
        return clubJpaRepository.findAll()
    }

    override fun findById(id: ClubId): Club? {
        return clubJpaRepository.findById(id).orElse(null)
    }

    override fun save(club: Club): Club {
        return clubJpaRepository.save(club)
    }
}