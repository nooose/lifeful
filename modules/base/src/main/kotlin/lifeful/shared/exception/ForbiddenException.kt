package lifeful.shared.exception

open class ForbiddenException(
    message: String,
    exception: Exception? = null,
) : NoStackTraceException(message, exception)
