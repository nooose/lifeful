package lifeful.member

@JvmInline
value class Email(
    val address: String,
) {
    init {
        require(EMAIL_REGEX.matches(address)) { "이메일($address) 형식이 바르지 않습니다." }
    }

    companion object {
        private val EMAIL_REGEX = Regex(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
        )
    }
}
