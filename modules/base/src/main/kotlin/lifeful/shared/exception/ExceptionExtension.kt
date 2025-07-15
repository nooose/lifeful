package lifeful.shared.exception

fun validateInput(boolean: Boolean, lazyException: () -> InvalidUserInputException) {
    if (!boolean) {
        throw lazyException()
    }
}

fun validateState(boolean: Boolean, lazyException: () -> DomainIllegalStateException) {
    if (!boolean) {
        throw lazyException()
    }
}
