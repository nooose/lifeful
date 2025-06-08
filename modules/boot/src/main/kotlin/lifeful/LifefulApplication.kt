package lifeful

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LifefulApplication

fun main(args: Array<String>) {
    runApplication<LifefulApplication>(*args)
}
