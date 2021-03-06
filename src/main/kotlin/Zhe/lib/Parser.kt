package Zhe.lib

val string: Parser = { input -> regex("\\w+")(input) }

val space: Parser = { input -> _regex("\\s+", true)(input) }

val constant: (String) -> Parser = {
    constant: String -> { input ->
        if(input.startsWith(constant))
            Result.Success(Token(constant), StringBuilder(input.substring(constant.length)))
        else
            Result.Fail("${constant} is not a prefix for input.")
    }
}

val regex: (String) -> Parser = {
    constant: String -> { input ->
        _regex(constant, false)(input)
    }
}

val _regex: (String, Boolean) -> Parser = {
    constant: String, discart: Boolean -> { input ->
        val regex = Regex("^" + constant)
        val match = regex.find(input)

        when (match) {
            null -> Result.Fail("Expected '$constant' got '$input'")
            else -> {
                val value = match.groupValues[0]

                var resp: Result
                if (discart)
                    resp = Result.Success(listOf(), StringBuilder(input.substring(value.length)))
                else
                    resp = Result.Success(Token(value), StringBuilder(input.substring(value.length)))
                resp
            }
        }
    }
}