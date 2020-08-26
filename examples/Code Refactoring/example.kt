import Zhe.lib.*

fun main(args: Array<String>) {
    val input: String = """
    if s != nil {
        for _ , x := range s {
        anything can go in here
        }
    }"""

    val runner = Runner()

    val FOR = regex("for") + group(many(string / ",", space)) + ":= range " + string +
              " \\{" + 
              text + 
               "\\}"

    val IF = regex("if ") + string + " != nil \\{" + 
            many(FOR, space) + 
            "}"
    
    runner.addEvent(IF,
    { vals ->
        // TODO: Implement a better model to handle the events
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