package lifeful.health

import org.springframework.modulith.ApplicationModule
import org.springframework.modulith.PackageInfo

@ApplicationModule(
    displayName = "건강(health)",
    allowedDependencies = [
        "member",
    ],
)
@PackageInfo
class ModuleMetadata
