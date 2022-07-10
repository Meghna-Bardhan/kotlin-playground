package nicestring

fun String.isNice(): Boolean {
    var isNice = false
    val containsSubstring = (!this.contains("bu") && !this.contains("ba") && !this.contains("be"))
    val vowels = listOf('a', 'e', 'i', 'o', 'u')
    val containsVowels = this.count { it -> vowels.contains(it) } >= 3

    var containsDoubleLetter = false
    this.forEachIndexed { index, letter ->
        if (index != 0) {
            if (this.get(index - 1) == letter) {
                containsDoubleLetter = true
            }
        }
    }

    isNice = listOf(containsSubstring, containsVowels, containsDoubleLetter).count { it } >= 2

    println("printing $isNice")
    return isNice
}