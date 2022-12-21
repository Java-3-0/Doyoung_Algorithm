import java.util.*
import kotlin.math.max

const val MAX_H = 4
const val MAX_W = 4

var H = 0
var W = 0
val grid = Array(MAX_H) { IntArray(4) }
var fullBitmask = 0
var answer = 0

fun main() = with(System.`in`.bufferedReader()) {
    val st = StringTokenizer(readLine()!!, " ")
    H = st.nextToken().toInt()
    W = st.nextToken().toInt()
    fullBitmask = (1 shl (H * W)) - 1

    for (y in 0 until H) {
        val line = readLine()!!
        for (x in 0 until W) {
            grid[y][x] = line[x].minus('0')
        }
    }

    solve(0, 0)

    println(answer)
}

fun solve(usedBitmask: Int, accum: Int) {
    if (usedBitmask == fullBitmask) {
        answer = max(answer, accum)
        return
    }

    val nowIdx = findFirstNotUsed(usedBitmask)

    val nowY = nowIdx / W
    val nowX = nowIdx % W

    // 가로로 써 보기
    var num = 0
    var nextBitmask = usedBitmask
    for (x in nowX until W) {
        val nextIdx = nowY * W + x
        if ((usedBitmask and (1 shl nextIdx)) == 0) {
            nextBitmask = (nextBitmask or (1 shl nextIdx))
            num *= 10
            num += grid[nowY][x]
            solve(nextBitmask, accum + num)
        } else {
            break
        }
    }

    // 세로로 써 보기
    num = 0
    nextBitmask = usedBitmask
    for (y in nowY until H) {
        val nextIdx = y * W + nowX
        if ((usedBitmask and (1 shl nextIdx)) == 0) {
            nextBitmask = (nextBitmask or (1 shl nextIdx))
            num *= 10
            num += grid[y][nowX]
            solve(nextBitmask, accum + num)
        } else {
            break
        }
    }
}

fun findFirstNotUsed(usedBitmask: Int): Int {
    for (i in 0 until H * W) {
        if ((usedBitmask and (1 shl i)) == 0) {
            return i
        }
    }

    return -1
}