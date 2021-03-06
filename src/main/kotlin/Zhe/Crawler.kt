package Zhe
import Zhe.lib.*
import java.net.*
import java.util.Scanner
import java.io.File
import kotlinx.coroutines.*

// }
fun main(args: Array<String>) {
    var aTag: Parser = constant("href=");

    val runner = Runner()

    runner.addEvent(aTag,
    { _ ->
        // println(vals)
    })
    println("name,size(kb),parsing,invoke,substring,mesured,total")
    // runBlocking {
    //     val jobsList: MutableList<Job> = mutableListOf()
        File("./experiments/4.2/files/pages_content").walkTopDown().forEach( { file ->
            if(!file.absolutePath.equals("/Users/joaosaffran/Zhe/./experiments/4.2/files/pages_content")){ 
                val stream = ZheStream(file.bufferedReader())
                val size = file.length()/1024
                print(file.name + "," + size + ",")
                var totalTime: Double = 0.0;
                val b = System.currentTimeMillis()
                while(stream.hasNext()){
                    val str = stream.next()
                    runner.invoke(str)
                    // println(str.length)
                }
                val e = System.currentTimeMillis()
                val t: Double = (e - b).toDouble()
                totalTime += t;
                runner.printTimes()
                println(",${totalTime/1000.0}")
            }
        })
        // jobsList.joinAll()
    // }    
}