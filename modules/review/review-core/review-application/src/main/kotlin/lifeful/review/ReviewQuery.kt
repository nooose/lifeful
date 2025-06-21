package lifeful.review

import lifeful.member.MemberPublicModel

data class ReviewQuery(
    val review: Review,
    val member: MemberPublicModel,
)
