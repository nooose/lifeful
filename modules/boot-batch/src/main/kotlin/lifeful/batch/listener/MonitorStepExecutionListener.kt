package lifeful.batch.listener

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.StepExecutionListener
import org.springframework.stereotype.Component

@Component
class MonitorStepExecutionListener : StepExecutionListener {
    private val log = KotlinLogging.logger {}

    override fun beforeStep(stepExecution: StepExecution) {
        log.info { "Step 감시 시작" }
    }

    override fun afterStep(stepExecution: StepExecution): ExitStatus {
        log.info { "Step 작업 종료" }
        log.info { "상태: ${stepExecution.status}" }
        return ExitStatus.COMPLETED
    }
}
