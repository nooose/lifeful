package readful

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ReadfulApplication

fun main(args: Array<String>) {
    runApplication<ReadfulApplication>(*args)
}
