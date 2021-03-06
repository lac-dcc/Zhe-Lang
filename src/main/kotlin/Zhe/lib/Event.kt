package Zhe.lib

public interface IEvent {
    public fun parse(input: StringBuilder): Result

    public fun invoke(values: List<Token<*>>): Unit
}

public class Event(val parser: Parser, val function: (values: List<Token<*>>) -> Unit) : IEvent {
    public override fun parse(input: StringBuilder): Result {
        return this.parser.apply(input)
    }

    public override fun invoke(values: List<Token<*>>) {
        // val map = values.map { t -> t.name to t}.toMap()

        this.function(values)
    }
}

public class SequencingEvent : IEvent {

    private val _stateTable: MutableMap<Int, Result.Success>
    private val _parsers: Array<Parser>
    private val _function: (values: List<Token<*>>) -> Unit

    constructor(parsers: List<Parser>, function: (values: List<Token<*>>) -> Unit) {
        this._stateTable = mutableMapOf()
        this._parsers = parsers.toTypedArray()
        this._function = function
    }

    public override fun parse(input: StringBuilder): Result = 
        this._parsers.mapIndexed{ index, parser -> 
            var result = parser(input)
            result = when(result){
                is Result.Success ->{
                    this._stateTable.set(index, result)
                    result
                }
                is Result.Fail -> {
                    var resp: Result? = this._stateTable.get(index)
                    if(resp == null)
                        resp = Result.Fail("Not Match")
                    resp
                }
            }
            result
        }.reduce { acc, cur ->
            when{
                acc is Result.Fail || cur is Result.Fail -> Result.Fail("Not Match")
                else -> {
                    val sAcc = acc as Result.Success
                    val sCur = cur as Result.Success
                    
                    val minRest: StringBuilder = when(sAcc.rest.length < sCur.rest.length) { 
                        true -> sAcc.rest
                        else -> sCur.rest
                      }
                    Result.Success(sAcc.tokens + sCur.tokens, minRest)                
                }
            }
    }

    public override fun invoke(values: List<Token<*>>) {
        // val map = values.map { t -> t.name to t}.toMap()
        this._function(values)
    }
}
