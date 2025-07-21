package lifeful.health.routine.command

import lifeful.shared.id.MemberId

data class RoutineCreateCommand(
    val memberId: MemberId,
    val name: String,
    val items: List<RoutineItemCreateCommand>,
)
