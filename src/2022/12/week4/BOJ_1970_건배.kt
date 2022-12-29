import kotlin.math.max

var N = 0
const val MAX_PEOPLE = 1000
const val MAX_BRANDS = 100
const val CACHE_EMPTY = -1

val brandList = mutableListOf<Int>()
val cache = Array(MAX_PEOPLE) { IntArray(MAX_PEOPLE) { CACHE_EMPTY } }

fun main(): Unit = with(System.`in`.bufferedReader()) {
    N = readLine()!!.toInt()
    brandList.addAll(readLine()!!.split(" ").map { it.toInt() })

    val answer = solve(0, N - 1)
    println(answer)
}

fun solve(left: Int, right: Int): Int {
    if (left >= right) {
        return 0
    }

    if (cache[left][right] != CACHE_EMPTY) {
        return cache[left][right]
    }


    // 맨 왼쪽 것을 아무하고도 연결하지 않는 경우
    var ret = solve(left + 1, right)

    // 맨 왼쪽 것을 연결 시도하는 경우
    val leftVal = brandList[left]
    for (i in left + 1 .. right) {
        if (brandList[i] == leftVal) {
            val a = solve(left + 1, i - 1)
            val b = solve(i + 1, right)
            ret = max(ret, 1 + a + b)
        }
    }

    cache[left][right] = ret
    return ret
}
