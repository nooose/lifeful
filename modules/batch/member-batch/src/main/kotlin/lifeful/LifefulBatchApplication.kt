package lifeful

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class LifefulBatchApplication

fun main(args: Array<String>) {
    runApplication<LifefulBatchApplication>(*args)
}
