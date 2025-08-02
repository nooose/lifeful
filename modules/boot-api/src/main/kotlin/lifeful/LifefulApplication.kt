package lifeful

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.modulith.Modulith

@ConfigurationPropertiesScan
@Modulith(
    sharedModules = [
        "shared",
    ],
)
@SpringBootApplication
class LifefulApplication

fun main(args: Array<String>) {
    runApplication<LifefulApplication>(*args)
}
