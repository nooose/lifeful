package lifeful.workout

import jakarta.validation.Valid
import lifeful.shared.id.RoutineId
import lifeful.workout.dto.request.RoutineCreateRequest
import lifeful.workout.dto.request.RoutineModifyRequest
import lifeful.workout.dto.response.RoutineResponse
import lifeful.workout.routine.command.RoutineCommandService
import lifeful.workout.routine.query.RoutineQueryService
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
    private val routineCommandService: RoutineCommandService,
    private val routineQueryService: RoutineQueryService,
) : RoutineApi {
    @PostMapping("/routines")
    override fun createRoutine(
        @RequestBody request: RoutineCreateRequest,
        @AuthenticationPrincipal loginMemberId: Long,
    ) {
        routineCommandService.createRoutine(command = request.toCommand(loginMemberId))
    }

    @PutMapping("/routines/{routineId}")
    override fun modifyRoutine(
        @PathVariable routineId: Long,
        @RequestBody @Valid request: RoutineModifyRequest,
        @AuthenticationPrincipal loginMemberId: Long,
    ) {
        routineCommandService.modifyRoutine(
            id = RoutineId(routineId),
            command = request.toCommand(loginMemberId),
        )
    }

    @GetMapping("/routines/{routineId}")
    override fun getRoutine(
        @PathVariable routineId: Long,
        @AuthenticationPrincipal loginMemberId: Long,
    ): RoutineResponse {
        val routine = routineQueryService.getRoutine(
            routineId = RoutineId(routineId),
        )
        return RoutineResponse.from(routine)
    }
}
