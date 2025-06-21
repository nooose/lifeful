rootProject.name = "lifeful"

include(
    "modules:boot",
    "modules:base",
    "modules:security",
    "modules:support:api-doc",
    "modules:book:core:application",
    "modules:book:core:domain",
    "modules:book:api",
    "modules:book:data:db",
    "modules:review:core:application",
    "modules:review:core:domain",
    "modules:review:api",
    "modules:review:data:db",
)
