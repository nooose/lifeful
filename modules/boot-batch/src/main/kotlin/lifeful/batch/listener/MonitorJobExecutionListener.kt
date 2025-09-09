package lifeful.batch.listener

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobExecutionListener
import org.springframework.stereotype.Component

@Component
class MonitorJobExecutionListener : JobExecutionListener {
    private val log = KotlinLogging.logger {}

    override fun beforeJob(jobExecution: JobExecution) {
        log.info { "Job 감시 시작" }
        jobExecution.executionContext.put("code", "noose")
    }

    override fun afterJob(jobExecution: JobExecution) {
        log.info { "Job 작업 종료" }
        log.info { "상태: ${jobExecution.status}" }
    }
}
