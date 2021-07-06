import kotlin.math.cos
import kotlin.math.sin

fun main() = readLine()!!.toDouble().let { println(sin(it) - cos(it)) }