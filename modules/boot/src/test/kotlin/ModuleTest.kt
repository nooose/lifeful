
import io.kotest.core.spec.style.StringSpec
import lifeful.LifefulApplication
import org.springframework.modulith.core.ApplicationModules
import org.springframework.modulith.docs.Documenter

class ModuleTest : StringSpec({
    val modules = ApplicationModules.of(LifefulApplication::class.java)

    "모듈 참조 테스트" {
        modules.forEach { println(it) }
        modules.verify()
    }

    "모듈 문서화" {
        Documenter(modules)
            .writeModulesAsPlantUml()
            .writeIndividualModulesAsPlantUml()
    }
})
