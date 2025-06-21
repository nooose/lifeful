package lifeful.member

import org.springframework.modulith.ApplicationModule
import org.springframework.modulith.PackageInfo

@ApplicationModule(
    id = "member",
    allowedDependencies = [
        "shared",
        "support",
    ],
)
@PackageInfo
class ModuleMetadata
