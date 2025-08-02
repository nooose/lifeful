package lifeful.health.routine.command

import io.github.oshai.kotlinlogging.KotlinLogging
import lifeful.member.MemberRegisteredEvent
import org.springframework.modulith.events.ApplicationModuleListener
import org.springframework.stereotype.Service

@Service
internal class RoutineAutoGenerator {
    private val log = KotlinLogging.logger { }

    @ApplicationModuleListener
    fun generate(event: MemberRegisteredEvent) {
        log.info { "${event.memberId} 기본 루틴 자동 생성" }
    }
}
