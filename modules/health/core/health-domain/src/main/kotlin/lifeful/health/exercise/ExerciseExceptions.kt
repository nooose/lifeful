package lifeful.health.exercise

import lifeful.shared.exception.NoStackTraceException

class ExerciseNotFoundException(
    message: String,
) : NoStackTraceException(message)

class MuscleGroupRequiredException(
    message: String,
) : NoStackTraceException(message)
