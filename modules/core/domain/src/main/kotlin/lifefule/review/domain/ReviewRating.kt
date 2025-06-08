package lifefule.review.domain

/**
 * 후기 평점 값
 * @author hd15807@gmail.com
 */
@JvmInline
value class ReviewRating(
    val value: Double,
) {
    init {
        require(value in MIN..MAX) { "후기 점수는 $MIN ~ $MAX 범위여야 합니다." }
        require((value * 2).rem(1.0) == 0.0) { "후기 점수는 0.5 단위만 허용됩니다." }
    }

    companion object {
        private const val MIN = 0.5
        private const val MAX = 5.0
        val FIVE_STAR = ReviewRating(MAX)
    }
}
