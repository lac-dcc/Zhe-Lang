package Zhe.lib

sealed class Result {
    data class Success<T>(val tokens: List<T>, val rest: String) : Result() {
        constructor(value: T, rest: String) : this(listOf(value), rest)

        override fun then(parser: Parser): Result {
            val result = parser(this.rest)
            return when (result) {
                is Fail -> result
                is Success<*> -> Success(this.tokens + result.tokens, result.rest)
            }
        }
    }

    data class Fail(val error: String) : Result() {
        override fun then(parser: Parser): Result {
            return this
        }
    }

    abstract fun then(parser: Parser): Result
}
