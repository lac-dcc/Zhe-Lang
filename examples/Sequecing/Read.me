# Introduction

Given that `E1` and `E2` are two events definied within the Zhe Lang.
`E1 &: E2`: this is the monad operator. The main idea of that operator
is that the order of E1 and E2 and also the occurrence of events in 
between matter. So, if the events don't appear on the exact same order 
they were specified, this guard won't be triggered.

## Example

Zhe Lang Specification:

```
Input File:

12/13/14 10:11.11 - System Output: fizz 78.642,23 fizz - System Status: true

Zhe File:

event Boolean = 'true' | 'false'
event Number = -?[0-9\.]+,[0-9]*
event FizzBuzz = 'fizz' | 'buzz'

guard Boolean &: Number (events[]){ ... }
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

    val guard: Parser = Boolean + Number

    runner.addEvent(
        Event(guard,
        { values -> ... }
    ))

    runner.invoke(INPUT)
}
```

## Output
In this scenario, the `Boolean &: Number` guard won't be triggered since
there is no occurrence of a Boolean event immediately followed by a Number event. 
Notice that the `Number &: Boolean` won't be triggered either, by the same 
reason as the `Boolean &: Number` guard. If we want to make a guard which triggers, 
we can write the following guard `Number &: * &: Boolean`, the values for this guard will 
be the following:

Boolean: true
FizzBuzz: fizz
Number: 78.642,23