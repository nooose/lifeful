package lifeful.health

import org.springframework.modulith.ApplicationModule
import org.springframework.modulith.PackageInfo

@ApplicationModule(
    id = "workout",
    allowedDependencies = [
        "shared",
    ],
)
@PackageInfo
class ModuleMetadata
