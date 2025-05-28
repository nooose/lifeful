package readful.ui.web.club

import readful.core.domain.club.Club
import readful.core.domain.shared.ClubId

data class ClubResponse(
    val id: ClubId,
    val title: String,
    val description: String,
    val memberCount: Int,
) {
    companion object {
        fun from(club: Club): ClubResponse = ClubResponse(
            id = club.id,
            title = club.title,
            description = club.description,
            memberCount = club.memberMaxCount,
        )
    }
}