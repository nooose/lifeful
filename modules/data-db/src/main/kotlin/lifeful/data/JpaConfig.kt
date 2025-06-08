package lifeful.data

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories(basePackages = ["lifeful.data"])
@EntityScan(basePackages = ["lifefule.domain"])
@Configuration(proxyBeanMethods = false)
class JpaConfig
