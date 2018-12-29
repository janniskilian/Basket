package de.janniskilian.basket.feature.lists.addlistitem

class ListItemInputParser {

    private val quantitySeparatorRegex by lazy {
        Regex("""[,;\-]+\s*(([0-9]+)\s*([a-zA-Z0-9]+)?)""")
    }
    private val alphaNameInputRegex by lazy {
        Regex("""([\p{L},;\-\s]*[\p{L}])(\s+([0-9]+)?\s*([\p{L}]+)?)?""")
    }
    private val alphaNumNameInputRegex by lazy {
        Regex("""([\p{L}0-9,;\-\s]*[\p{L}0-9])\s*[,\-]+\s*(([0-9]+)?\s*([\p{L}]+)?)?""")
    }

    fun parse(input: String): Result {
        val normalizedInput = input.trim()

        val regex = if (quantitySeparatorRegex.containsMatchIn(normalizedInput)) {
            alphaNumNameInputRegex
        } else {
            alphaNameInputRegex
        }
        val groupValues = regex.matchEntire(normalizedInput)?.groups

        return Result(
            groupValues?.get(MATCH_GROUP_NAME)?.value ?: normalizedInput,
            groupValues?.get(MATCH_GROUP_QUANTITY)?.value,
            groupValues?.get(MATCH_GROUP_UNIT)?.value
        )
    }

    class Result(
        val name: String,
        val quantity: String?,
        val unit: String?
    )

    companion object {

        private const val MATCH_GROUP_NAME = 1
        private const val MATCH_GROUP_QUANTITY = 3
        private const val MATCH_GROUP_UNIT = 4
    }
}
