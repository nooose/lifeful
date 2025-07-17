rootProject.name = "lifeful"

include(
    "modules:boot-api",
    "modules:boot-batch",
    "modules:base",
    "modules:security",
)

include(
    "modules:support:api-doc",
)

include(
    "modules:member:core:member-application",
    "modules:member:core:member-domain",
    "modules:member:member-api",
    "modules:member:member-integration",
)

include(
    "modules:workout:core:workout-application",
    "modules:workout:core:workout-domain",
    "modules:workout:workout-jpa",
    "modules:workout:workout-api",
)
