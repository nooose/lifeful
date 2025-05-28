package readful.data.book.client.aladin

import readful.core.domain.book.Book

data class AladinBookSearchResponse(
    val item: List<AladinItemResponse> // ⬅️ 여기서 리스트로 받아야 함
)

data class AladinItemResponse(
    val title: String?,
    val author: String?,
    val description: String?,
    val isbn13: String?,
    val publisher: String?,
) {

    fun toDomain(): Book {
        return Book(
            isbn = isbn13 ?: "",
            title = title ?: "",
            description = description ?: "",
            author = author ?: "작가미상",
            publisher = publisher ?: ""
        )
    }
}
