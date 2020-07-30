package Zhe.lib;

public interface IEvent {
    public fun parse(input: String): Result;
    
    public fun invoke(values: List<Any?>): Unit;
}

public class Event(val parser: Parser, val function: (values: List<Any?>) -> Unit) : IEvent {
    public override fun parse(input: String): Result {
        return this.parser.apply(input)
    }

    public override fun invoke(values: List<Any?>): Unit {
        this.function(values)
    }
}