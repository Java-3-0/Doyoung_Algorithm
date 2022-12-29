import java.util.*
import kotlin.math.min

const val MAX_N = 1000
const val MAX_M = 1000
const val CACHE_EMPTY = -1

var N = 0
var WIDTH = 0
val nameLengthList = mutableListOf<Int>()
val cache = Array(MAX_N + 1) { IntArray(MAX_M + 1) { CACHE_EMPTY } }

fun main(): Unit = with(System.`in`.bufferedReader()) {
    var st = StringTokenizer(readLine()!!, " ")
    N = st.nextToken().toInt()
    WIDTH = st.nextToken().toInt()

    repeat(N) {
        nameLengthList.add(readLine()!!.toInt())
    }

    val answer = solve(0, 0)

    println(answer)
}

fun solve(startIdx: Int, remainingWidth: Int): Int {
    if (startIdx == N) {
        return 0
    }

    if (cache[startIdx][remainingWidth] != CACHE_EMPTY) {
        return cache[startIdx][remainingWidth]
    }

    val nameLen = nameLengthList[startIdx]

    // 다음 줄에 배치하는 경우
    var ret = remainingWidth * remainingWidth + solve(startIdx + 1, WIDTH - nameLen)

    // 같은 줄에 이어서 배치하는 경우
    if (nameLen + 1 <= remainingWidth) {
        ret = min(ret, solve(startIdx + 1, remainingWidth - (nameLen + 1)))
    }

    cache[startIdx][remainingWidth] = ret
    return ret
}
