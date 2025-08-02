rootProject.name = "lifeful"

include(
    "modules:boot-api",
    "modules:boot-batch",
    "modules:base",
    "modules:security",
)

include(
    "modules:support:api-doc",
    "modules:support:api-common",
)

include(
    "modules:member:core:member-application",
    "modules:member:core:member-domain",
    "modules:member:core:member-event",
    "modules:member:member-api",
    "modules:member:member-integration",
)

include(
    "modules:health:core:health-application",
    "modules:health:core:health-domain",
    "modules:health:health-api",
)

include(
    "modules:event:core:event-application",
    "modules:event:core:event-domain",
)

include(
    "modules:admin:admin-api",
)
