// You can experiment here, it won’t be checked

fun solution(first: List<Int>, second: List<Int>): MutableList<Int> {
    val solutionList = first.toMutableList()
    solutionList.addAll(second)
    return solutionList
}

fun main(args: Array<String>) {
    val products = listOf(0, 1, 2, 3)
    val dadsProducts = listOf(4, 5, 6)

    println(solution(products, dadsProducts))
}
