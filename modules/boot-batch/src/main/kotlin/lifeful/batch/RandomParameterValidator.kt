package lifeful.batch

import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersValidator
import org.springframework.stereotype.Component

@Component
class RandomParameterValidator : JobParametersValidator {
    override fun validate(parameters: JobParameters?) {
        requireNotNull(parameters) { "JobParameters is null." }

        val max = parameters.getLong("max")
            ?: throw IllegalArgumentException("max 파라미터가 존재하지 않습니다.")

        require(max > 50) { "max 파라미터는 50보다 커야 합니다." }
    }
}
