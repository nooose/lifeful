package lifeful.shared.exception

open class InvalidUserInputException(
    message: String,
    exception: Exception? = null,
) : NoStackTraceException(message, exception)
