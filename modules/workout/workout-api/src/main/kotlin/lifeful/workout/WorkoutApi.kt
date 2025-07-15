package lifeful.workout

import io.swagger.v3.oas.annotations.tags.Tag
import lifeful.shared.RequiredAuthorization

@RequiredAuthorization
@Tag(
    name = "운동 기록 API",
    description = "운동 기록 관련 API",
)
internal interface WorkoutApi
