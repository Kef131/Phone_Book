import kotlin.math.PI
import kotlin.math.acos
import kotlin.math.pow
import kotlin.math.sqrt

fun main() {
    val firstVector = readLine()!!.split(" ").map { it.toDouble() }
    val secondVector = readLine()!!.split(" ").map { it.toDouble() }
    val firstLengthVector = sqrt(firstVector[0].pow(2) + firstVector[1].pow(2))
    val secondLengthVector = sqrt(secondVector[0].pow(2) + secondVector[1].pow(2))
    val numerator = firstVector[0] * secondVector[0] + firstVector[1] * secondVector[1]
    val denominator = firstLengthVector * secondLengthVector
    val solutionRadians = acos(numerator / denominator)
    val solution = solutionRadians * 180 / PI
    println(solution)
}