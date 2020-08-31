package Zhe.lib

sealed class Result {
    data class Success(val tokens: List<Token<*>>, val rest: String) : Result() {
        constructor(value: Token<*>, rest: String) : this(listOf(value), rest);

        override fun then(parser: Parser): Result {
            val result = parser(this.rest)
            return when (result) {
                is Fail -> result
                is Success -> Success(this.tokens + result.tokens, result.rest)
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
