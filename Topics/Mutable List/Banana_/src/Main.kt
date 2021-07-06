fun solution(strings: MutableList<String>, str: String): MutableList<String> {
    for ((index, string) in strings.withIndex()) {
        if (string == str) strings[index] = "Banana"
    }
    return strings
}