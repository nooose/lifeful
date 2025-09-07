package lifeful.batch

import org.springframework.batch.core.converter.JobParametersConverter
import org.springframework.batch.core.converter.JsonJobParametersConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration(proxyBeanMethods = false)
class BatchConfig {

    @Bean
    fun jobParametersConverter(): JobParametersConverter {
        return JsonJobParametersConverter()
    }
}
