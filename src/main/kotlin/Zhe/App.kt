package Zhe
import Zhe.lib.*

fun main(args: Array<String>) {
    val input: String = """
    if s != nil {
        for _ , x := range s {
        anything can go in here
        }
    }"""

    val runner = Runner()

    val FOR = regex("for") + name("forVar1", group(many(string / ",", space))) + ":= range " + name("forVar2", string) +
              " \\{" + 
              name("body", text) + 
               "\\}"

    val IF = regex("if ") + name("ifVar", string) + " != nil \\{" + 
            many(FOR, space) + 
            "}"
    
    runner.addEvent(IF,
    { vals ->
        // TODO: Implement a better model to handle the events
        val forVar1 = vals.get("forVar1") 
        val forVar2 = vals.get("forVar2")
        val ifVar = vals.get("ifVar")
        val body = vals.get("body")

        println(forVar1)
        println(forVar2)
        println(ifVar)
        println(body)

        if( ifVar?.value == forVar2?.value){
            println("Original code:\n$input\n\n")
            println("Code Rewritten: ")
            println("for ${forVar1} := range ${ifVar} {\n\t${body}\n}")
        }
    })

    runner.invoke(input)
}