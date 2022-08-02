package nicestring

fun String.isNice(): Boolean {

    val noBadSubString = setOf("bu","ba","be").none{this.contains(it)}
    val hasThreeVowels = count {it in "aeiou"}>=3
    val hasDouble = zipWithNext().any{it.first == it.second}

    return listOf(noBadSubString, hasThreeVowels, hasDouble).count{it} >= 2
}