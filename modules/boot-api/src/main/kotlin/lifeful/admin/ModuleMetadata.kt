package lifeful.admin

import org.springframework.modulith.ApplicationModule
import org.springframework.modulith.PackageInfo

@ApplicationModule(
    displayName = "관리자(admin)",
    allowedDependencies = [
        "event",
    ],
)
@PackageInfo
class ModuleMetadata
