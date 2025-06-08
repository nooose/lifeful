package lifefule.book.domain

/**
 * 책 제공 클라이언트
 *
 * @author hd15807@gmail.com
 */
interface BookClient {
    /**
     * 검색 조건으로 책 데이터를 외부에서 가져온다.
     * @param isbn 책 ISBN
     * @param title 책 제목
     * @return 책 도메인 모델, 찾지 못하면 null을 반환한다.
     */
    fun getBook(
        isbn: String?,
        title: String?,
    ): Book?
}
