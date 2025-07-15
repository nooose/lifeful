package lifeful.member

enum class MemberStatus(
    val description: String,
) {
    PENDING("가입 대기"),
    ACTIVE("가입 완료"),
    DEACTIVATED("탈퇴"),
}
