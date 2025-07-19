package lifeful.workout.routine

import jakarta.validation.Valid
import lifeful.shared.id.RoutineId
import lifeful.workout.routine.command.RoutineCreate
import lifeful.workout.routine.command.RoutineModify
import lifeful.workout.routine.dto.request.RoutineCreateRequest
import lifeful.workout.routine.dto.request.RoutineModifyRequest
import lifeful.workout.routine.dto.response.RoutineResponse
import lifeful.workout.routine.query.RoutineFinder
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class RoutineRestController(
    private val routineCreate: RoutineCreate,
    private val routineModify: RoutineModify,
    private val routineFinder: RoutineFinder,
) : RoutineApi {
    @PostMapping("/routines")
    override fun createRoutine(
        @RequestBody request: RoutineCreateRequest,
        @AuthenticationPrincipal loginMemberId: Long,
    ) {
        routineCreate.create(command = request.toCommand(loginMemberId))
    }

    @PutMapping("/routines/{routineId}")
    override fun modifyRoutine(
        @PathVariable routineId: Long,
        @RequestBody @Valid request: RoutineModifyRequest,
        @AuthenticationPrincipal loginMemberId: Long,
    ) {
        routineModify.modify(
            id = RoutineId(routineId),
            command = request.toCommand(loginMemberId),
        )
    }

    @GetMapping("/routines/{routineId}")
    override fun getRoutine(
        @PathVariable routineId: Long,
        @AuthenticationPrincipal loginMemberId: Long,
    ): RoutineResponse {
        val routine = routineFinder.get(
            routineId = RoutineId(routineId),
        )
        return RoutineResponse.from(routine)
    }
}
