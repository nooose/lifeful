package lifeful.member

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["lifeful.member"])
class TestApplication

fun main(args: Array<String>) {
    runApplication<TestApplication>(*args)
}
