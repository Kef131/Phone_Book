import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    val population = scanner.next()
    println(Math.cbrt(population.toDouble()).toInt())
}
