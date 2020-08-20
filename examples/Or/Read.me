# Introduction

Given that `E1` or `E2` are two events definied within the Zhe Lang.
E1 or E2: this defines the logical or operator. The guard will follow 
the same principals as the and guard, the order and the occurrence of 
other events in the middle don't affect this guard.

## Example

Zhe Lang Specification:

```
Input File:

12/13/14 10:11.11 - System Output: fizz 78.642,23 fizz - System Status: true

Zhe File:

event Boolean = 'true' | 'false'
event Number = -?[0-9\.]+,[0-9]*
event FizzBuzz = 'fizz' | 'buzz'

guard Boolean or Number (events[]){ ... }
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

    val guard: Parser = Boolean / Number

    runner.addEvent(
        Event(guard,
        { values -> ... }
    ))

    runner.invoke(INPUT)
}
```

## Output
In this scenario, the Boolean or Number guard will be triggered 7 times, with the following values:
1. Boolean: null
  Number:  12

2. Boolean: null
  Number:  13

3. Boolean: null
  Number:  14

4. Boolean: null
  Number:  10

5. Boolean: null
  Number:  11.11

6. Boolean: null
  Number:  78.642,23

7. Boolean: true
  Number:  78.642,23