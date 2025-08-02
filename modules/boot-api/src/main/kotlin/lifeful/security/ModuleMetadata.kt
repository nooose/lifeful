package lifeful.security

import org.springframework.modulith.ApplicationModule
import org.springframework.modulith.PackageInfo

@ApplicationModule(
    displayName = "보안(security)",
    allowedDependencies = [
        "member",
    ],
)
@PackageInfo
class ModuleMetadata
