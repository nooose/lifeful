package lifeful.base

import org.springframework.test.web.servlet.assertj.MockMvcTester
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder

object CustomMvcTester {
    fun mvcTester(
        vararg controllers: Any,
    ): MockMvcTester {
        return MockMvcTester.of(*controllers, { builder: StandaloneMockMvcBuilder ->
            builder.setHandlerExceptionResolvers()
                .alwaysDo<StandaloneMockMvcBuilder>(MockMvcResultHandlers.print())
                .build()
        })
    }
}
