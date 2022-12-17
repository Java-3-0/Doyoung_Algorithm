val matchedIdxSet = HashSet<Int>()
var answer = 0

fun main() = with(System.`in`.bufferedReader()) {
    val N = readLine().toInt()
    val arrA = readLine().split(" ").map { it.toInt() }.toIntArray()
    val arrB = readLine().split(" ").map { it.toInt() }.toIntArray()

    for (i in arrA.indices) {
        if (arrA[i] == arrB[i]) {
            matchedIdxSet.add(i)
        }
    }

    if (matchedIdxSet.size == N) {
        answer = 1
    }
    insertionSort(N, arrA, 0, arrA.size - 1, arrB)

    println(answer)
}

fun insertionSort(N: Int, arrA: IntArray, left: Int, right: Int, arrB: IntArray) {
    for (i in 1 until N) {
        var loc = i - 1
        var newItem = arrA[i]

        while (0 <= loc && newItem < arrA[loc]) {
            arrA[loc + 1] = arrA[loc]

            if (arrA[loc + 1] == arrB[loc + 1]) {
                matchedIdxSet.add((loc + 1))
            } else {
                matchedIdxSet.remove((loc + 1))
            }

            if (matchedIdxSet.size == N) {
                answer = 1
            }

            loc--;
        }

        if (loc + 1 != i) {
            arrA[loc + 1] = newItem

            if (arrA[loc + 1] == arrB[loc + 1]) {
                matchedIdxSet.add((loc + 1))
            } else {
                matchedIdxSet.remove((loc + 1))
            }

            if (matchedIdxSet.size == N) {
                answer = 1
            }
        }
    }
}