# Introduction

This examples outlines how to implement a simple parser to extract 
strong links in a html file.

## Example

### Zhe Lang Specification:
```
Input File:
<a>normal text</a> 
<a><strong>bold text</strong></a

event tag = (<string>) text (</string>)
event strong = <strong> (text | tag) </strong>
event a = <a> (text | tag) </a>

Zhe File:

guard a(strong) (events) {
    println("Some Really important TEXT:")

    val first = vals.indexOfLast { e -> e == "<strong>"}
    val last = vals.indexOfLast { e -> e == "</strong>"}
    
    print(vals.subList(first + 1, last))
}
```

### Zhe Lib Specification:
```kotlin
import Zhe.lib.*

  val INPUT: String = """<a>normal text</a> 
                         <a><strong>bold text</strong></a>"""

    val runner = Runner()

    val TAG = regex("<") + string + ">" + many(string, space) + "</" + string + ">"
    val STRONG = regex("<strong>") + many(string / tag, space) + "</strong>"
    val A = regex("<a>") + many(string / tag, space) + "</a>"
    
    runner.addEvent(A contains STRONG,
    { vals ->
        println("Some Really important TEXT:")
        val first = vals.indexOfLast { e -> e == "<strong>"}
        val last = vals.indexOfLast { e -> e == "</strong>"}
        
        print(vals.subList(first + 1, last))
    })

    runner.invoke(INPUT)
```

## Output
```
Some Really important TEXT:
[bold, text]
```