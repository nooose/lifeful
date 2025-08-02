package lifeful.event

data class EventPublishCommand(
    val eventPayload: Any,
    val listenerId: String,
)
