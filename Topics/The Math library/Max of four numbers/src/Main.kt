fun main() {
    val numbers = mutableListOf<Int>()
    repeat(4) {
        numbers.add(readLine()!!.toInt())
    }
    println(numbers.maxOrNull())
}