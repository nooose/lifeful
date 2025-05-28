package readful

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class ReadfulApplication

fun main(args: Array<String>) {
    runApplication<ReadfulApplication>(*args)
}
