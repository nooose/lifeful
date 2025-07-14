package lifeful.shared.exception

open class NoStackTraceException(
    message: String,
    exception: Exception? = null,
) : RuntimeException(
        message,
        exception,
    ) {
    override fun fillInStackTrace(): Throwable {
        return this
    }
}
