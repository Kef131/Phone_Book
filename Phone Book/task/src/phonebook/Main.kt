package phonebook

import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.floor
import kotlin.math.sqrt

var beginningTime = 0L
var finalTime = 0L
var linealSearchTime = 0L
var sortingBubbleTime = 0L
var quickSortTime = 0L

// TODO: Convert it to data class Name
fun List<String>.getCompleteName(index: Int): String {
    var nameArray = this[index].split(" ")
    return if (nameArray.size > 2) {
        "${nameArray[1]} ${nameArray[2]}"
    } else {
        nameArray[1]
    }
}

fun List<String>.getNumber(index: Int): String {
    var nameArray = this[index].split(" ")
    return nameArray[0]
}


fun hashTable(directoryFile: File, findFile: File) {
    val directoryList = directoryFile.readLines()
    val findNameList = findFile.readLines()
    val directoryHashMap = mutableMapOf<String, String>()
    //Inserting data to hashtable
    beginningTime = System.currentTimeMillis()
    for (index in 0..directoryList.lastIndex) {
        directoryHashMap[directoryList.getCompleteName(index)] = directoryList.getNumber(index)
    }
    finalTime = System.currentTimeMillis()
    val totalInsertingTime = finalTime - beginningTime
    //Searching in hashtable
    var foundNames = 0
    beginningTime = System.currentTimeMillis()
    for (index in 0..findNameList.lastIndex) {
        if (directoryHashMap.contains(findNameList[index]))
            foundNames++
    }
    finalTime = System.currentTimeMillis()
    val totalTimeSearching = finalTime - beginningTime
    println("Found $foundNames / ${findNameList.size} entries. Time taken: ${String.format("%1\$tM min. %1\$tS sec. %1\$tL ms.", totalInsertingTime + totalTimeSearching)}")
    println("Creating time:: ${String.format("%1\$tM min. %1\$tS sec. %1\$tL ms.", totalInsertingTime)}")
    println("Searching time: ${String.format("%1\$tM min. %1\$tS sec. %1\$tL ms.", totalTimeSearching)}")
}

val pickPivotMethod = {list: List<String> ->
    //Take first, middle and the last value of teh array and choose the median
    val length = list.lastIndex
    var (first, middle, last) = arrayOf("", "", "")
    first = list.getCompleteName(0)
    if (length > 1) {
        middle = list.getCompleteName(length / 2)
        last = list.getCompleteName(length)
    } else {
        middle = first
        last = first
    }
    var mean =
        if (first > middle && first < last) first
        else if (middle > first && middle < last) middle
        else last
    mean
}

//Function that choose pivot and reorder elements, recursively
fun quickSort(unsortedNameList: List<String>): List<String>{
    // Base case: if is 0 or 1 size, return the list itself
    if (unsortedNameList.size == 1 || unsortedNameList.isEmpty())
        return unsortedNameList
    //Pick pivot -
    var pivot = pickPivotMethod(unsortedNameList)
    var leftList = mutableListOf<String>()
    var rightList = mutableListOf<String>()
    var middle = mutableListOf<String>()
    //start to order|ADD to the left SUBARRAY the lesser than pivot and to right SUBARRAY greater than pivot
    for (indexName in 0..unsortedNameList.lastIndex) {
        when {
            // value lesser than pivot - recursion QUICKSORT on LEFT SUBARRAY
            unsortedNameList.getCompleteName(indexName) < pivot -> leftList.add(unsortedNameList[indexName])
            // value greater than pivot  - recursion QUICKSORT on RIGHT SUBARRAY
            unsortedNameList.getCompleteName(indexName) > pivot -> rightList.add(unsortedNameList[indexName])
            // value equal than pivot
            else -> middle.add(unsortedNameList[indexName])
        }
    }
    return quickSort(leftList) + middle + quickSort(rightList)
}

fun quickSortMeasure(directoryFile: File): List<String> {
    beginningTime = System.currentTimeMillis()
    val sortedList = quickSort(directoryFile.readLines())
    finalTime = System.currentTimeMillis()
    quickSortTime = finalTime - beginningTime
    return sortedList
}

fun binarySearchInFile(orderedList: List<String>, findFile: File) {
    val findNameList = findFile.readLines()
    beginningTime = System.currentTimeMillis()
    var foundNames = 0
    for (indexName in 0..findNameList.lastIndex) {
        if (binarySearch(orderedList, findNameList[indexName]))
            foundNames++
    }
    finalTime = System.currentTimeMillis()
    val searchingTime = finalTime - beginningTime
    println("Found $foundNames / ${findNameList.size} entries. Time taken: ${String.format("%1\$tM min. %1\$tS sec. %1\$tL ms.", quickSortTime + searchingTime)}")
    println("Sorting time: ${String.format("%1\$tM min. %1\$tS sec. %1\$tL ms.", quickSortTime)}")
    println("Searching time: ${String.format("%1\$tM min. %1\$tS sec. %1\$tL ms.", searchingTime)}")
}

fun binarySearch(orderedList: List<String>, nameToFind: String): Boolean {
    // BASE CASE: Check middle element of array
    val middleElement = orderedList.getCompleteName(orderedList.lastIndex / 2)
    return if (nameToFind == middleElement)
        true
    // RECURSIVE STEP: Go left if value is lesser that middle element, or right otherwise
    else if (nameToFind < middleElement && orderedList.size > 1)
        binarySearch(orderedList.subList(0, orderedList.size / 2), nameToFind)
    else if (nameToFind > middleElement && orderedList.size > 1)
        binarySearch(orderedList.subList(orderedList.size / 2, orderedList.size), nameToFind)
    else
        false
}

fun bubbleSort(directoryFile: File, findFileExtra: File): List<String> {
    val directoryMutableList = directoryFile.readLines().toMutableList()
    var swapCounter = -1
    var boundaryList = directoryMutableList.lastIndex
    beginningTime = System.currentTimeMillis()
    while (swapCounter != 0) {
        swapCounter = 0
        for (i in 0 until boundaryList) {
            // Need to check if the complete name is greater than the next, so swap it
            if (directoryMutableList.getCompleteName(i) > directoryMutableList.getCompleteName(i + 1)) {
                val temp = directoryMutableList[i]
                directoryMutableList[i] = directoryMutableList[i + 1]
                directoryMutableList[i + 1] = temp
                swapCounter++
            }
        }
        boundaryList--
        // If sorting process takes 10 times more than the linear search, stop and do Linear search
        val stopTimeSort = System.currentTimeMillis() - beginningTime
        if (stopTimeSort > linealSearchTime * 10 ) {
            sortingBubbleTime = stopTimeSort
            linearSearch(findFileExtra, directoryFile, true)
            println("Sorting time: ${String.format("%1\$tM min. %1\$tS sec. %1\$tL ms.", stopTimeSort)} - STOPPED, moved to linear search")
            println("Searching time: ${String.format("%1\$tM min. %1\$tS sec. %1\$tL ms.", linealSearchTime)}")
            return emptyList()
        }
    }
    finalTime = System.currentTimeMillis()
    sortingBubbleTime = finalTime - beginningTime
    return directoryMutableList.toList()
}

fun jumpSearch(sortedNameList: List<String>, findFile: File) {
    if (sortedNameList.isNotEmpty()) {
        val findNameList = findFile.readLines()
        val jumpLength = floor(sqrt(sortedNameList.size.toDouble())).toInt()
        var namesFound = 0
        beginningTime = System.currentTimeMillis()
        for (name in findNameList) {
            var currentIndex = 0
            var search = true
            nextName@ while (search) {
                //Stop condition
                if (currentIndex > sortedNameList.lastIndex) {
                    search = false
                    currentIndex = sortedNameList.size
                }
                val currentNameInList = sortedNameList.getCompleteName(currentIndex)
                // Search in very first element | Search in the index of the jump
                if (name == currentNameInList) {
                    namesFound++
                    break@nextName
                } else if (currentNameInList > name) {
                    // Backward search, if not find here, break and go the next one
                    val leftIndex = currentIndex - jumpLength + 1
                    val rightIndex = currentIndex - 1
                    for (i in rightIndex downTo leftIndex) {
                        val currentNameInListBackward = sortedNameList.getCompleteName(i)
                        if (name == currentNameInListBackward) {
                            namesFound++
                            break@nextName
                        }
                    }
                    break@nextName
                } else {
                    currentIndex += jumpLength
                }
            }
        }
        finalTime = System.currentTimeMillis()
        val searchingTime = finalTime - beginningTime
        println("Found $namesFound / ${findNameList.size} entries. Time taken: ${String.format("%1\$tM min. %1\$tS sec. %1\$tL ms.", sortingBubbleTime + searchingTime)}")
        println("Sorting time: ${String.format("%1\$tM min. %1\$tS sec. %1\$tL ms.", sortingBubbleTime)}")
        println("Searching time: ${String.format("%1\$tM min. %1\$tS sec. %1\$tL ms.", searchingTime)}")
    }
}

fun linearSearch(findFile: File, directoryFile: File, fromSort: Boolean = false) {
    val findNameFile = findFile.readLines()
    val directoryData = directoryFile.readText()
    var namesFound = 0
    beginningTime = System.currentTimeMillis()
    for (name in findNameFile)
        if (directoryData.contains(name))
            namesFound++
    finalTime = System.currentTimeMillis()
    linealSearchTime = finalTime - beginningTime
    val formatter = SimpleDateFormat("m 'min.' s 'sec.' S 'ms.'")
    var timeTaken = ""
    if (fromSort) {
        timeTaken = formatter.format(Date(linealSearchTime + sortingBubbleTime))
        println("Found $namesFound / ${findNameFile.size} entries. Time taken: ${timeTaken}")
    } else {
        timeTaken = formatter.format(Date(linealSearchTime))
        println("Found $namesFound / ${findNameFile.size} entries. Time taken: $timeTaken\n")
    }
}

fun main() {
    val directoryFile = File("D:\\directory.txt")
    val findFile = File("D:\\find.txt")
    val smallDirectoryFile = File("D:\\small_directory.txt")
    val smallFindFile = File("D:\\small_find.txt")
    println("Start searching (linear search)...")
    linearSearch(findFile, directoryFile)
    println("Start searching (bubble sort + jump search)...")
    jumpSearch(bubbleSort(directoryFile, findFile), findFile)
    println("\nStart searching (quick sort + binary search)...")
    binarySearchInFile(quickSortMeasure(directoryFile), findFile)
    println("\nStart searching (hash table)...")
    hashTable(directoryFile, findFile)
}