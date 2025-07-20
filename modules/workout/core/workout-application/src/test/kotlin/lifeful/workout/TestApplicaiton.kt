package lifeful.workout

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["lifeful.workout"])
class TestApplication

fun main(args: Array<String>) {
    runApplication<TestApplication>(*args)
}
