package lifeful.shared.exception

open class UnauthorizedException(
    message: String,
    exception: Exception? = null,
) : NoStackTraceException(message, exception)
