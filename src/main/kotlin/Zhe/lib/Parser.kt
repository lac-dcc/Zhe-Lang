package Zhe.lib

public typealias Parser = (input: String) -> Result

operator fun Parser.plus(parser: Parser): Parser {
    return { b: String -> this(b).then(parser) }
}

operator fun Parser.plus(constant: String): Parser {
    return { b: String -> this(b).then(regex(constant)) }
}

infix fun Parser.apply(input: String): Result = this(input)

val integer: Parser = { input ->
    val regex = Regex("^\\d+")
    val match = regex.find(input)

    when (match) {
        null -> Result.Fail("Expected integer got '$input'")
        else -> {
            val value = match.groupValues[0]
            Result.Success<Int>(value.toInt(), input.substring(value.length))
        }
    }
}

val plus:   Parser = { input -> regex("\\+")(input)  }

val string: Parser = { input -> regex("\\w+")(input) }

val space:  Parser = { input -> regex("\\s+")(input) }

val regex: (String) -> Parser = {
    constant: String -> { input: String ->
        val regex = Regex("^" + constant)
        val match = regex.find(input)

        when (match) {
            null -> Result.Fail("Expected '$constant' got '$input'")
            else -> {
                val value = match.groupValues[0]
                Result.Success<String>(value, input.substring(value.length))
            }
        }
    }
}
