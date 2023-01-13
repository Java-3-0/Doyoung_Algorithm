import java.util.*

const val MAX_H = 50
const val MAX_W = 50
const val MAX_V = MAX_H * MAX_W

const val MAX_DOMAIN = MAX_V
const val MAX_RANGE = MAX_V
const val NOT_MATCHED = -1
const val EMPTY = '.'
const val WALL = 'X'

var DOMAIN = MAX_DOMAIN
var RANGE = MAX_RANGE

val grid = Array(MAX_H + 1) { CharArray(MAX_W + 1) { EMPTY } }
val adjList = Array(MAX_DOMAIN + 1) { mutableListOf<Int>() }
val isChecked = BooleanArray(MAX_RANGE + 1) { false }
val isMatchedTo = IntArray(MAX_RANGE + 1) { NOT_MATCHED }

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val (H, W) = readLine()!!.trim().split(" ").map { it.toInt() }
    DOMAIN = H * W
    RANGE = H * W

    for (y in 0 until H) {
        val line = readLine()!!
        for (x in 0 until W) {
            grid[y][x] = line[x]
        }
    }

    for (y in 0 until H) {
        for (x in 0 until W) {
            if (grid[y][x] == EMPTY) {
                val idx1 = y * W + x
                val isInDomain = (y + x) % 2 == 0

                val nextY = y + 1
                val nextX = x + 1
                if (nextY < H && grid[nextY][x] == EMPTY) {
                    val idx2 = nextY * W + x
                    if (isInDomain) {
                        adjList[idx1].add(idx2)
                    } else {
                        adjList[idx2].add(idx1)
                    }
                }
                if (nextX < W && grid[y][nextX] == EMPTY) {
                    val idx2 = y * W + nextX
                    if (isInDomain) {
                        adjList[idx1].add(idx2)
                    } else {
                        adjList[idx2].add(idx1)
                    }
                }
            }
        }
    }

    // 2 x 1 타일을 최대한 채우고 남은 칸만큼은 1 x 1로 채워야 한다
    val maxMatches = getMaxBipartiteMatches()

    var cntEmpty = 0
    for (y in 0 until H) {
        for (x in 0 until W) {
            if (grid[y][x] ==EMPTY) {
                cntEmpty++
            }
        }
    }
    val answer = maxMatches + (cntEmpty - 2 * maxMatches)
    println(answer)
}

/** 이분매칭 최대 매칭 수 리턴 */
fun getMaxBipartiteMatches(): Int {
    var ret = 0

    for (from in 0 until DOMAIN) {
        Arrays.fill(isChecked, false)
        if (dfs(from)) {
            ret++
        }
    }

    return ret
}


/** dfs 를 수행하면서 매칭 시도  */
fun dfs(from: Int): Boolean {
    for (to in adjList[from]) {
        if (!isChecked[to]) {
            isChecked[to] = true
            if (isMatchedTo[to] == NOT_MATCHED || dfs(isMatchedTo[to])) {
                isMatchedTo[to] = from
                return true
            }
        }
    }

    return false
}