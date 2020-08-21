package Zhe.lib

public interface IEvent {
    public fun parse(input: String): Result

    public fun invoke(values: List<Any?>): Unit
}

public class Event(val parser: Parser, val function: (values: List<Any?>) -> Unit) : IEvent {
    public override fun parse(input: String): Result {
        return this.parser.apply(input)
    }

    public override fun invoke(values: List<Any?>) {
        this.function(values)
    }
}

public class SequencingEvent : IEvent {

    private val _stateTable: MutableMap<Int, Result.Success<*>>
    private val _parsers: Array<Parser>
    private val _function: (values: List<Any?>) -> Unit

    constructor(parsers: List<Parser>, function: (values: List<Any?>) -> Unit) {
        this._stateTable = mutableMapOf()
        this._parsers = parsers.toTypedArray()
        this._function = function
    }

    public override fun parse(input: String): Result {
        val initial: Result = Result.Success<Any?>(listOf<Any?>(), input)

        var anyEventParsed: Boolean = false

        val parserResult = this._parsers.foldIndexed(initial) { index, curr, parser ->
            var result: Result = parser.apply(input)

            if (result is Result.Fail) {
                anyEventParsed = anyEventParsed || false
                val aux = this._stateTable.get(index)
                result = when (aux) {
                    null -> Result.Fail("Not in Table")
                    else -> aux
                }
            } else if (result is Result.Success<*>) {
                anyEventParsed = true
                this._stateTable.set(index, result)
            }

            when {
                curr is Result.Success<*> && result is Result.Success<*> -> {
                    val rest = if (result.rest.length < curr.rest.length) result.rest else curr.rest
                    Result.Success(curr.tokens + result.tokens, rest)
                }
                else -> Result.Fail("Some Parser is Still Missing")
            }
        }

        return when (anyEventParsed) { true -> parserResult false -> Result.Fail("No Event Ocurred") }
    }

    public override fun invoke(values: List<Any?>) {
        this._function(values)
        // this._stateTable.clear()
    }
}
