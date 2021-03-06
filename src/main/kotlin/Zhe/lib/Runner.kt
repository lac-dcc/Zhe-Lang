package Zhe.lib

public class Runner {
    private val _events: MutableList<IEvent>
    private var parsingTime = 0.0
    private var invokeTime = 0.0
    private var copyingTime = 0.0

    constructor() {
        this._events = mutableListOf<IEvent>()
    }

    private fun addEvent(event: IEvent): Int {
        this._events.add(event)
        return this._events.size - 1
    }

    public fun addEvent(parser: Parser, function: (values:List<Token<*>>) -> Unit): Int =
        this.addEvent(Event(parser, function))

    public fun addEvent(parsers: List<Parser>, function: (values:List<Token<*>>) -> Unit): Int =
        this.addEvent(SequencingEvent(parsers, function))

    public fun removeEvent(event: IEvent) {
        this._events.remove(event)
    }

    public fun removeEvent(index: Int) {
        this._events.removeAt(index)
    }

    public fun printTimes(){
        print("${this.parsingTime},${this.invokeTime},${this.copyingTime}")
        this.parsingTime = 0.0;
        this.invokeTime = 0.0;
        this.copyingTime = 0.0;
    }

    public fun invoke(input: String) {
        this._events.forEach({ event: IEvent ->
            this.invoke(event, input);
        })
    }

    private fun invoke(event: IEvent, input: String) {
        var remain: StringBuilder = StringBuilder(input);
        while(remain.length > 0){
            val b = System.currentTimeMillis()
            val result = event.parse(remain)
            val e = System.currentTimeMillis()
            this.parsingTime += e - b
            
            when (result) {
                is Result.Success -> {
                    val b1 = System.currentTimeMillis()
                    event.invoke(result.tokens) 
                    val e1 = System.currentTimeMillis()
                    this.invokeTime += e1 - b1

                    val b2 = System.currentTimeMillis()
                    remain = result.rest
                    val e2 = System.currentTimeMillis()
                    this.copyingTime += e2 - b2
                }
                is Result.Fail -> {
                    val b3 = System.currentTimeMillis()
                    remain = remain.deleteCharAt(0)
                    val e3 = System.currentTimeMillis()
                    this.copyingTime += e3 - b3
                }
            }
        }        
    }
}
