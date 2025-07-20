package lifeful.base

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import org.springframework.transaction.annotation.Transactional

@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class IntegrationTest

@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class AcceptanceTest
