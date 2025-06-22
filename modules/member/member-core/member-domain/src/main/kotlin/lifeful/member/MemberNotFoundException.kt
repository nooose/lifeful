package lifeful.member

class MemberNotFoundException(
    override val message: String,
) : RuntimeException(message)
