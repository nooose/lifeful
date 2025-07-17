package lifeful

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class LifefulApplication

fun main(args: Array<String>) {
    runApplication<LifefulApplication>(*args)
}
