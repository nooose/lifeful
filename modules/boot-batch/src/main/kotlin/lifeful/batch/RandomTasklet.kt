package lifeful.batch

import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
@StepScope
class RandomTasklet(
    @Value("#{jobParameters['id']}" )
    val id: String,
    @Value("#{jobParameters['max']}")
    val max: Int,
) : Tasklet {
    override fun execute(
        contribution: StepContribution,
        chunkContext: ChunkContext
    ): RepeatStatus {
        log.info { "ID: $id" }
        val random = (0..max).random()
        log.info { "랜덤 생성 코드: $random" }

        if (random < 50) {
            return RepeatStatus.CONTINUABLE
        }

        return RepeatStatus.FINISHED
    }
}
