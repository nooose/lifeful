package lifeful.shared.exception

open class DomainIllegalStateException(
    message: String,
    exception: Exception? = null,
) : NoStackTraceException(message, exception)
