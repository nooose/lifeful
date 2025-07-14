package lifeful.shared.exception

fun require(boolean: Boolean, lazyException: () -> InvalidUserInputException) {
    if (!boolean) {
        throw lazyException()
    }
}

fun check(boolean: Boolean, lazyException: () -> DomainIllegalStateException) {
    if (!boolean) {
        throw lazyException()
    }
}
