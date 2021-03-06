package Zhe
import Zhe.lib.*
import java.net.*
import java.util.Scanner
import java.io.File
import kotlinx.coroutines.*
import java.io.BufferedReader;

class ZheStream(val file: BufferedReader, val limit: Int=1000) {
    var buffer: StringBuilder = StringBuilder("")
    var eof = false

    public fun next():String {
        var resp:StringBuilder;
        if(buffer.length >= this.limit){
            resp = StringBuilder(buffer.substring(0, this.limit))
            buffer.delete(0, this.limit)
        }else{
            resp = StringBuilder(buffer)
            buffer.clear()
        }

        while(resp.length < this.limit){
            var curLine = file.readLine();
            if(curLine == null){
                eof = true
                break
            }

            if(resp.length + curLine.length >= this.limit ){
                val offset = Math.abs(this.limit - resp.length);
                this.buffer = StringBuilder(curLine.substring(offset))
                resp.append(curLine.substring(0, offset))
            }else {
                resp.append(curLine)
            }
        }
        return resp.toString();
    }

    public fun hasNext():Boolean {
        if(this.eof)
            return false

        if(!this.buffer.isEmpty())
            return true

        val line = this.file.readLine()
        if(line == null){
            eof = true
            return false
        }
        this.buffer.append(line)
        return true
    }
}

fun main(args: Array<String>) {
    var aTag: Parser = constant("SELECT") + regex(".*\\)") + constant("FROM") + regex("(\\w=\\w(\\s,\\s)?)+");
    val runner = Runner()

    runner.addEvent(aTag,
    { _ ->
        // println(vals)
    })
    println("file,size(kb),time_parsing,time_invoke,time_substring,time_zhe")
    File("./experiments/4.1/obtain_oltp_logs/logs/").walkTopDown().forEach( { file ->
        
        if(!file.absolutePath.equals("/Users/joaosaffran/Zhe/./experiments/4.1/obtain_oltp_logs/logs")){
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
    });
}