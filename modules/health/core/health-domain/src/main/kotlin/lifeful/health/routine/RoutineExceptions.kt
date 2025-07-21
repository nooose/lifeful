package lifeful.health.routine

import lifeful.shared.exception.InvalidUserInputException
import lifeful.shared.exception.ResourceNotFoundException

class RoutineNotFoundException(
    message: String,
) : ResourceNotFoundException(message)

class RoutineException(
    message: String,
) : InvalidUserInputException(message)
