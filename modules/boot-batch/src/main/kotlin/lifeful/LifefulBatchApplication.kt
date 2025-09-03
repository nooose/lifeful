package lifeful

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import kotlin.system.exitProcess

@EnableScheduling
@SpringBootApplication
class LifefulBatchApplication

fun main(args: Array<String>) {
    exitProcess(
        SpringApplication.exit(
            runApplication<LifefulBatchApplication>(*args)
        )
    )
}
