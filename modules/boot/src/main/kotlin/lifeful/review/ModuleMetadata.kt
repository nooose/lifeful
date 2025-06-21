package lifeful.review

import org.springframework.modulith.ApplicationModule
import org.springframework.modulith.PackageInfo

@ApplicationModule(
    id = "review",
    allowedDependencies = [
        "shared",
        "security",
        "support",
        "member :: member-finder",
        "member :: member-public-model",
    ],
)
@PackageInfo
class ModuleMetadata
