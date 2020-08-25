import Zhe.lib.*

fun main(args: Array<String>) {
    val input: String = """<a>normal text</a> 
<a><strong>bold text</strong></a>"""

    val runner = Runner()

    val tag = regex("<") + string + ">" + many(string, space) + "</" + string + ">"
    val strong = regex("<strong>") + many(string / tag, space) + "</strong>"
    val a = regex("<a>") + many(string / tag, space) + "</a>"
    
    runner.addEvent(a contains strong,
    { vals ->
        println("Some Really important TEXT:")
        val first = vals.indexOfLast { e -> e == "<strong>"}
        val last = vals.indexOfLast { e -> e == "</strong>"}
        
        print(vals.subList(first + 1, last))
    })

    runner.invoke(input)
}
