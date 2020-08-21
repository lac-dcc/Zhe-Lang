package Zhe.lib

public class Runner {
    private val _events: MutableList<IEvent>

    constructor() {
        this._events = mutableListOf<IEvent>()
    }

    private fun addEvent(event: IEvent): Int {
        this._events.add(event)
        return this._events.size - 1
    }

    public fun addEvent(parser: Parser, function: (values: List<Any?>) -> Unit): Int =
        this.addEvent(Event(parser, function))

    public fun addEvent(parsers: List<Parser>, function: (values: List<Any?>) -> Unit): Int =
        this.addEvent(SequencingEvent(parsers, function))

    public fun removeEvent(event: IEvent) {
        this._events.remove(event)
    }

    public fun removeEvent(index: Int) {
        this._events.removeAt(index)
    }

    public fun invoke(input: String) {
            this._events.forEach({ event: IEvent ->
                var remainingString: String = input

                do{
                    val result = event.parse(remainingString)
                    when (result) {
                        is Result.Success<*> -> {
                            event.invoke(result.tokens)
                            remainingString = result.rest
                        }
                        is Result.Fail -> {
                            remainingString = remainingString.substring(1)
                            Unit
                        }
                    }
                }while(remainingString.length > 0);
            })
    }
}
