import Zhe.lib.*

val INPUT: String = "12/13/14 10:11.11 - System Output: fizz 78.642,23 fizz - System Status: true"

val BOOLEAN: Parser = regex('true') / 'false';
val NUMBER: Parser = number
val FIZZBUZZ: Parser = regex('fizz') / 'buzz'

fun main(args: Array<String>) {
    val runner = Runner()

    val guard: Parser = BOOLEAN / NUMBER

    runner.addEvent(
        SequencingEvent(guard,
        { values ->
            println(values)
        }
    ))

    runner.invoke(INPUT)
}