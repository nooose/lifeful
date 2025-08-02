package lifeful.event

data class EventPublishCommand(
    val payload: Any,
    val listenerId: String,
)
