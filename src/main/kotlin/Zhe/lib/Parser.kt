package Zhe.lib

val integer: Parser = { input ->
    val regex = Regex("^\\d+")
    val match = regex.find(input)

    when (match) {
        null -> Result.Fail("Expected integer got '$input'")
        else -> {
            val value = match.groupValues[0]
            Result.Success<Int>(listOf(value.toInt()), input.substring(value.length))
        }
    }
}

val plus: Parser = { input -> regex("\\+")(input) }

val string: Parser = { input -> regex("\\w+")(input) }

val space: Parser = { input -> regex("\\s+")(input) }

val regex: (String) -> Parser = {
    constant: String -> { input: String ->
        val regex = Regex("^" + constant)
        val match = regex.find(input)

        when (match) {
            null -> Result.Fail("Expected '$constant' got '$input'")
            else -> {
                val value = match.groupValues[0]
                Result.Success<String>(listOf(value), input.substring(value.length))
            }
        }
    }
}
