import kotlin.math.sqrt

fun main() {
    val a = readLine()!!.toInt()
    val b = readLine()!!.toInt()
    val c = readLine()!!.toInt()
    val p = (a + b + c) / 2.0
    println(sqrt(p * (p - a) * (p - b) * (p - c)))
}