# Introduction

This examples outlines how to implement a code refactor example,
taken from the paper: Lightweight Multi-Language Syntax Transformation
with Parser Parser Combinators.

## Example

Zhe Lang Specification:

```
Input File: 

if s != nil {
    for _ , x := range s {
        anything can go in here
    }
}

Zhe File:

event FOR = for (string | ,)+ := range string { text }

event IF = if string != nil { FOR }

guard IF (events) {
    val forVar1 = events.get(4);
    val forVar2 = events.get(6)
    val ifVar = events.get(1)
    val body = events.get(8)

    if( ifVar == forVar2){
        println("Original code:\n$input\n\n")
        println("Code Rewritten: ")
        println("for ${forVar1} := range ${ifVar} {\n\t${body}\n}")
    }
}
```

Zhe Lib Specification:


```kotlin

val input: String = """
    if s != nil {
        for _ , x := range s {
        anything can go in here
        }
    }"""

val FOR = regex("for") + group(many(string / ",", space)) + ":= range " + string +
              " \\{" + 
              text + 
               "\\}"

val IF = regex("if ") + string + " != nil \\{" + 
            many(FOR, space) + 
            "}"

fun main(args: Array<String>) {

    val runner = Runner()
    
    runner.addEvent(IF,
    { vals ->
        val forVar1 = vals.get(4);
        val forVar2 = vals.get(6)
        val ifVar = vals.get(1)
        val body = vals.get(8)

        if( ifVar == forVar2){
            println("Original code:\n$input\n\n")
            println("Code Rewritten: ")
            println("for ${forVar1} := range ${ifVar} {\n\t${body}\n}")
        }
    })

    runner.invoke(input)
}
```

## Output
```
Code Rewritten:
for _ , x := range s {
        anything can go in here
}
```