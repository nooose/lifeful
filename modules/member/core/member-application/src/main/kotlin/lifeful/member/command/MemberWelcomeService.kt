package lifeful.member.command

import io.github.oshai.kotlinlogging.KotlinLogging
import lifeful.member.MemberRegisteredEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.event.TransactionalEventListener

@Service
internal class MemberWelcomeService {
    private val log = KotlinLogging.logger {}

    @Async
    @TransactionalEventListener
    fun sendEmail(event: MemberRegisteredEvent) {
        log.info { "회원(${event.memberId}) 가입 이메일 전송" }
    }

    @Async
    @TransactionalEventListener
    fun initSuggestion(event: MemberRegisteredEvent) {
        log.info { "회원(${event.memberId}) 추천 정보 초기화" }
    }
}
