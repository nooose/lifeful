package lifeful

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

val log = KotlinLogging.logger {}

@Service
class MemberBatchService {

    @Scheduled(fixedRate = 1_000)
    fun batch() {
        log.info { "사용자 배치 처리 로직" }
    }
}
