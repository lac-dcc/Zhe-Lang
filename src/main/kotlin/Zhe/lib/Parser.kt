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

val space: Parser = { input -> _regex("\\s+", true)(input) }

val regex: (String) -> Parser = {
    constant: String -> { input ->
        _regex(constant, false)(input)
    }
}

val _regex: (String, Boolean) -> Parser = {
    constant: String, discart: Boolean -> { input: String ->
        val regex = Regex("^" + constant)
        val match = regex.find(input)

        when (match) {
            null -> Result.Fail("Expected '$constant' got '$input'")
            else -> {
                val value = match.groupValues[0]

                var resp: Result
                if (discart)
                    resp = Result.Success<String>(listOf(), input.substring(value.length))
                else
                    resp = Result.Success<String>(listOf(value), input.substring(value.length))
                resp
            }
        }
    }
}

val many: (Parser, Parser) -> Parser = {
    target, separator: Parser ->
         { input: String ->
            var parser = target / separator

            var curString = input
            var tokens: MutableList<Any?> = mutableListOf()

            do {
                val curResp = parser(curString)
                when (curResp) {
                    is Result.Fail -> separator(curString)
                    is Result.Success<*> -> {
                        tokens = (tokens + curResp.tokens).toMutableList()
                        curString = curResp.rest
                    }
                }
            } while (curResp is Result.Success<*>)

            var resp: Result

            if (tokens.isEmpty())
                resp = Result.Fail("Parser not aplicable")
            else
                resp = Result.Success<Any?>(tokens, curString)
            resp
        }
}

val text: Parser = { input -> 
    _group(many(string, space), " ")(input)
}

val group: (Parser) -> Parser = { parser -> 
    {input -> 
        _group(parser, "")(input)
    }
}

val _group: (Parser, String) -> Parser = { parser, separator ->
    { input ->
         val result = parser(input)

         when(result){
             is Result.Fail -> result
             is Result.Success<*> -> Result.Success<String>(
                                     listOf(result.tokens.joinToString(separator)), 
                                     result.rest)
         }
    }
}