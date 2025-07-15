package lifeful.shared.exception

open class ResourceNotFoundException(
    message: String,
    exception: Exception? = null,
) : NoStackTraceException(message, exception)
