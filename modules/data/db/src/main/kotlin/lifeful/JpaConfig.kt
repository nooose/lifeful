package lifeful

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories(
    basePackages = [
        "lifeful.book.data",
        "lifeful.review.data",
    ],
)
@EntityScan(
    basePackages = [
        "lifefule.book.domain",
        "lifefule.review.domain",
    ],
)
@Configuration(proxyBeanMethods = false)
class JpaConfig
