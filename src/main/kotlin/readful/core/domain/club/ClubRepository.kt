package readful.core.domain.club

import readful.core.domain.shared.ClubId

interface ClubRepository {
    fun findAll(): List<Club>
    fun findById(id: ClubId): Club?
    fun save(club: Club): Club
}
