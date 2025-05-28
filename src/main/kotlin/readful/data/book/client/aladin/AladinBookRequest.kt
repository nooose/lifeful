package readful.data.book.client.aladin

data class AladinBookRequest(
    val ttbkey: String,            // 필수: 알라딘 TTB 키
    val query: String,             // 필수: 검색어(기본값 타이틀 검색)
    val maxResults: Int = 100,     // 기본값: 검색 결과 수
    val start: Int = 1,            // 기본값: 검색 시작 위치
    val output: String = "js",     // 기본값: 응답 형식: js가 json 형식
    val version: String = "20131101"   // API 버전: 20131101이 최신
)
