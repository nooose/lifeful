rootProject.name = "lifeful"

include(
    "modules:boot",
    "modules:base",
    "modules:security",
    "modules:support:api-doc",
    "modules:book:book-core:book-application",
    "modules:book:book-core:book-domain",
    "modules:book:book-api",
    "modules:book:book-data:book-db",
    "modules:review:review-core:review-application",
    "modules:review:review-core:review-domain",
    "modules:review:review-api",
    "modules:review:review-data:review-db",
)
