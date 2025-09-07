package lifeful.batch

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

val log = KotlinLogging.logger {}

@Configuration
class TestBatchService(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
) {
    @Bean
    fun simulationJob(randomStep: Step, validator: RandomParameterValidator): Job {
        return JobBuilder("simulationJob", jobRepository)
            .validator(validator)
            .start(startStep())
            .next(processStep())
            .next(randomStep)
            .next(completeStep())
            .build()
    }

    @Bean
    fun startStep(): Step {
        return StepBuilder("startStep", jobRepository)
            .tasklet(
                { contribution, chunkContext ->
                    log.info { "배치 시작" }
                    RepeatStatus.FINISHED
                }, transactionManager)
            .build()
    }

    @Bean
    fun processStep(): Step {
        return StepBuilder("meetStep", jobRepository)
            .tasklet(
                { contribution, chunkContext ->
                    log.info { "배치 프로세스" }
                    RepeatStatus.FINISHED
                }, transactionManager)
            .build()
    }

    @Bean
    fun randomStep(randomTasklet: RandomTasklet): Step {
        return StepBuilder("randomStep", jobRepository)
            .tasklet(randomTasklet, transactionManager)
            .build()
    }

    @Bean
    fun completeStep(): Step {
        return StepBuilder("completeStep", jobRepository)
            .tasklet(
                { contribution, chunkContext ->
                    log.info { "배치 완료" }
                    RepeatStatus.FINISHED
                }, transactionManager)
            .build()
    }
}
