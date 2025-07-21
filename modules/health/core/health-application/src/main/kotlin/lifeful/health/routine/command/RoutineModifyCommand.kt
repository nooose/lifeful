package lifeful.health.routine.command

import lifeful.shared.id.MemberId

data class RoutineModifyCommand(
    val memberId: MemberId,
    val name: String,
    val items: List<RoutineItemCreateCommand>,
)
