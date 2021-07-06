import kotlin.math.pow
fun calculateDiscriminant(a: Double, b: Double, c: Double): Double {
    return b * b - 4 * a * c
}

fun calculateRoots(a: Double, b: Double, c: Double, discriminant: Double) {
    val x1 = (-b + discriminant.pow(0.5)) / (2 * a)
    val x2 = (-b - discriminant.pow(0.5)) / (2 * a)
    println(if (x1 < x2) "$x1 $x2" else "$x2 $x1")
}

fun main() {
    val a = readLine()!!.toDouble()
    val b = readLine()!!.toDouble()
    val c = readLine()!!.toDouble()
    calculateRoots(a, b, c, calculateDiscriminant(a, b, c))

}