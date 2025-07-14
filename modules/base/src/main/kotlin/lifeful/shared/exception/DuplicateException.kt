package lifeful.shared.exception

open class DuplicateException(
    message: String,
    exception: Exception? = null,
) : NoStackTraceException(message, exception)
