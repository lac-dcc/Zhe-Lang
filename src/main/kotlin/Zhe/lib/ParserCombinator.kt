package Zhe.lib

public typealias Parser = (input: String) -> Result

operator fun Parser.plus(parser: Parser): Parser {
    return { b: String -> this(b).then(parser) }
}

operator fun Parser.plus(constant: String): Parser {
    return { b: String -> this(b).then(regex(constant)) }
}

operator fun Parser.div(parser: Parser): Parser {
    return { b: String ->
        val result: Result = this(b)
        when (result) {
            is Result.Fail -> parser(b)
            is Result.Success<*> -> result
        }
    }
}

operator fun Parser.div(constant: String): Parser {
    return { b: String ->
       (this / regex(constant)).apply(b)
    }
}

infix fun Parser.`contains`(parser: Parser): Parser {
    return { b: String ->
        val result = this(b)

        when (result) {
            is Result.Fail -> result
            is Result.Success<*> -> {
                val newInput = b.substring(0, b.length - result.rest.length)
                var finalResult: Result = Result.Fail("The events are not nested")

                for (i in 0..newInput.length) {
                    val substr = newInput.substring(i)

                    finalResult = parser(substr)
                    if (finalResult is Result.Success<*>){
                        finalResult = Result.Success<Any?>(result.tokens + finalResult.tokens
                                                         , result.rest
                                                         )
                        break
                    }
                }

                finalResult
            }
        }
    }
}

infix fun Parser.`contains`(input: String): Parser {
    return { b: String -> (this contains regex(input)).apply(b) }
}

infix fun List<Parser>.`and`(parser: Parser): List<Parser> {
    return this + parser
}

infix fun Parser.`and`(parser: Parser): List<Parser> {
    return listOf(this, parser)
}

infix fun Parser.apply(input: String): Result = this(input)
