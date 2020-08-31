package Zhe.lib

val integer: Parser = { input ->
    val regex = Regex("^\\d+")
    val match = regex.find(input)

    when (match) {
        null -> Result.Fail("Expected integer got '$input'")
        else -> {
            val value = match.groupValues[0]
            Result.Success(Token<Int>(value.toInt()), input.substring(value.length))
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

val name: (String, Parser) -> Parser = { 
    name, parser -> {input: String ->
        val result = parser(input)

        when(result){
            is Result.Success -> {
                val newTokens = result.tokens.map { t-> t.rename(name) }
                Result.Success(newTokens, result.rest)
            }
            is Result.Fail -> result
        }
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
                    resp = Result.Success(listOf(), input.substring(value.length))
                else
                    resp = Result.Success(Token(value), input.substring(value.length))
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
            var tokens: MutableList<Token<*>> = mutableListOf()

            do {
                val curResp = parser(curString)
                when (curResp) {
                    is Result.Fail -> separator(curString)
                    is Result.Success -> {
                        tokens = (tokens + curResp.tokens).toMutableList()
                        curString = curResp.rest
                    }
                }
            } while (curResp is Result.Success)

            var resp: Result

            if (tokens.isEmpty())
                resp = Result.Fail("Parser not aplicable")
            else
                resp = Result.Success(tokens, curString)
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
             is Result.Success -> Result.Success(
                                     Token<String>(result.tokens.joinToString(separator).trim()), 
                                     result.rest)
         }
    }
}