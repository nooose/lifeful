package lifeful.base

import org.springframework.context.event.EventListener

class TestEvents {
    private val _events: MutableList<Any> = mutableListOf()
    val events: List<Any>
        get() = _events

    @EventListener
    internal fun addEvent(event: Any) {
        _events.add(event)
    }

    inline fun <reified T : Any> count(): Int = events<T>().count()

    inline fun <reified T : Any> first(): T = events<T>().first()

    inline fun <reified T : Any> events(): List<T> = events.filterIsInstance(T::class.java)

    fun clear() = _events.clear()
}
