package Zhe
import Zhe.lib.*
import java.net.*
import java.util.Scanner
import java.io.File
import kotlin.concurrent.*

// fun getFileContent(filename: String): String {

//     return File(filename).readLines().joinToString("\n");
// }

fun main(args: Array<String>) {
    var aTag: Parser = regex("for\\(") + regex(".*\\)") + space + constant("{") + regex(".*\\}");
    val runner = Runner()

    runner.addEvent(aTag,
    { vals ->
        // println(vals)
    })
    println("name,size(kb),parsing,invoke,substring,mesured,total")
    File("./experiments/4.3/angha/").walkTopDown().forEach( { file ->
         
        if(!file.absolutePath.equals("/Users/joaosaffran/Zhe/./experiments/4.3/angha")){
            val stream = ZheStream(file.bufferedReader())
                val size = file.length()/1024
                print(file.name + "," + size + ",")
                var totalTime: Double = 0.0;
                val b = System.currentTimeMillis()
                while(stream.hasNext()){
                    val str = stream.next()
                    runner.invoke(str)
                    println(str.length)
                }
                val e = System.currentTimeMillis()
                val t: Double = (e - b).toDouble()
                totalTime += t;
                runner.printTimes()
                println(",${totalTime/1000.0}") 
        }
    });
}