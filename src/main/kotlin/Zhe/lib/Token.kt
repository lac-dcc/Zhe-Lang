package Zhe.lib

private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
val randomString = {
    (1..8)
    .map { _ -> kotlin.random.Random.nextInt(0, charPool.size) }
    .map(charPool::get)
    .joinToString("")
}

public class Token<T>(val value: T, val name: String) {
    
    constructor(value: T): this(value, randomString())

    public fun rename (name: String): Token<T>{
        return Token<T>(this.value, name)
    }

    public override fun toString(): String {
        return this.value.toString()
    }
}
