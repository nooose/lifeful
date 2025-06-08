package lifeful.api

import org.springframework.modulith.ApplicationModule
import org.springframework.modulith.PackageInfo

@ApplicationModule(
    id = "api",
    allowedDependencies = [
        "book :: *",
        "review :: *",
        "security",
        "support",
    ],
)
@PackageInfo
class ModuleMetadata
