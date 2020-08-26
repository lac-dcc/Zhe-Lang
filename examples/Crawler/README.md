# Introduction

This examples outlines how to implement a simple parser to extract 
strong links in a html file.

## Example

### Zhe Lang Specification:
```
Input File:
<a>normal text</a> 
<a><strong>bold text</strong></a


Zhe File:

event tag = ("<"string">") text ("</"string">")
event strong = "<strong>" (text | tag) "</strong>"
event a = "<a>" (text | tag) "</a>"


guard a(strong) (events) {
    println("Some Really important TEXT:")

    val first = events.indexOfLast { e -> e == "<strong>"}
    val last = events.indexOfLast { e -> e == "</strong>"}
    
    print(events.subList(first + 1, last))
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
    { events ->
        println("Some Really important TEXT:")
        val first = events.indexOfLast { e -> e == "<strong>"}
        val last = events.indexOfLast { e -> e == "</strong>"}
        
        print(events.subList(first + 1, last))
    })

    runner.invoke(INPUT)
```

## Output
```
Some Really important TEXT:
[bold, text]
```