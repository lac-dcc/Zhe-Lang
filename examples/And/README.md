# Introduction

Given that `E1` and `E2` are two events definied within the Zhe Lang.
`E1 and E2`: this defines the logical and operator. The idea of this guard 
is to be triggered whenever both events occurred, regardless of the order 
they occur and regardless of the occurrence of other events in between those. 

## Example

Zhe Lang Specification:

```
Input File:

12/13/14 10:11.11 - System Output: fizz 78.642,23 fizz - System Status: true

Zhe File:

event Boolean = 'true' | 'false'
event Number = -?[0-9\.]+,[0-9]*
event FizzBuzz = 'fizz' | 'buzz'

guard Boolean and Number (events[]){ ... }
```

Zhe Lib Specification:


```kotlin
import Zhe.lib.*

val INPUT: String = "12/13/14 10:11.11 - System Output: fizz 78.642,23 fizz - System Status: true"

val Boolean: Parser = regex("true") / "false"
val Number: Parser = number
val FizzBuzz: Parser = regex("fizz") / "buzz"

fun main(args: Array<String>) {
    val runner = Runner()

    val guard: Array<Parser> = arrayOf(Boolean, Number)

    runner.addEvent(
        SequencingEvent(guard,
        { values -> ... }
    ))

    runner.invoke(INPUT)
}
```

## Output
In this scenario, the guard will be triggered only one time and the values for the events will be:
Boolean: true
Number: 78.642,23