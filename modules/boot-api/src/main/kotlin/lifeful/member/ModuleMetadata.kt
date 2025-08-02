package lifeful.member

import org.springframework.modulith.ApplicationModule
import org.springframework.modulith.PackageInfo

@ApplicationModule(
    displayName = "회원(member)",
    allowedDependencies = [
        "member",
    ],
)
@PackageInfo
class ModuleMetadata
