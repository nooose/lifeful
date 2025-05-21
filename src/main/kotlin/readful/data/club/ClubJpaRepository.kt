package readful.data.club

import org.springframework.data.jpa.repository.JpaRepository
import readful.core.domain.club.Club
import readful.core.domain.shared.ClubId

interface ClubJpaRepository : JpaRepository<Club, ClubId> {
}