package Zhe.lib

public typealias Parser = (input: StringBuilder) -> Result

operator fun Parser.plus(parser: Parser): Parser {
    return { b: StringBuilder -> this(b).then(parser) }
}

operator fun Parser.plus(constant: String): Parser {
    return { b: StringBuilder -> this(b).then(regex(constant)) }
}

operator fun Parser.div(parser: Parser): Parser {
    return { b: StringBuilder ->
        val result: Result = this(b)
        when (result) {
            is Result.Fail -> parser(b)
            is Result.Success -> result
        }
    }
}

operator fun Parser.div(constant: String): Parser {
    return { b: StringBuilder ->
       (this / regex(constant)).apply(b)
    }
}

infix fun Parser.apply(input: StringBuilder): Result = this(input)