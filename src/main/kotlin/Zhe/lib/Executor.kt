package Zhe.lib;

public class Runner {
    private val _events: MutableList<IEvent>;

    constructor() {
        this._events = mutableListOf<IEvent>()
    }

    public fun addEvent(event: IEvent): Int{
        this._events.add(event);
        return this._events.size - 1
    }

    public fun removeEvent(event: IEvent): Unit {
        this._events.remove(event)
    }

    public fun removeEvent(index: Int): Unit {
        this._events.removeAt(index)
    }

    public fun invoke(input: String): Unit {
        for( i in 0..input.length) {
            val curSubstring: String = input.substring(i)
            this._events.forEach({ event: IEvent ->
                val result = event.parse(curSubstring)
                when(result) {
                    is Result.Success<*> -> event.invoke(result.tokens)
                    is Result.Fail -> Unit
                }
            })
        }
    }
}