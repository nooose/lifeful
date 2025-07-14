rootProject.name = "lifeful"

include(
    "modules:boot",
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
)

include(
    "modules:workout:core:workout-application",
    "modules:workout:core:workout-domain",
    "modules:workout:data:workout-jpa",
    "modules:workout:workout-api",
)
