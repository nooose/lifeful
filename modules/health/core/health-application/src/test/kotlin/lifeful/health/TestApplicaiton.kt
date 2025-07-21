package lifeful.health

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["lifeful.health"])
class TestApplication

fun main(args: Array<String>) {
    runApplication<TestApplication>(*args)
}
