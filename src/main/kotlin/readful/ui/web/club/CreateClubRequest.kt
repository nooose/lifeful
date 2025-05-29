package readful.ui.web.club

data class CreateClubRequest(
    val title: String,
    val description: String,
    val memberCount: Int,
    val bookId: Long,
) {
}
