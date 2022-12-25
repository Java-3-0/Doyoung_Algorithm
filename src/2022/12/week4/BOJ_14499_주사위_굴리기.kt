import java.util.*

const val TOP = 0
const val EAST = 1
const val WEST = 2
const val NORTH = 3
const val SOUTH = 4
const val BOTTOM = 5

var H = 0
var W = 0
var curY = 0
var curX = 0

val dy = intArrayOf(0, 0, 0, -1, 1)
val dx = intArrayOf(0, 1, -1, 0, 0)
val dice = intArrayOf(0, 0, 0, 0, 0, 0)
var grid = arrayOf(intArrayOf())

fun main() = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    // 입력
    var st = StringTokenizer(readLine()!!, " ")
    H = st.nextToken().toInt()
    W = st.nextToken().toInt()
    curY = st.nextToken().toInt()
    curX = st.nextToken().toInt()
    val K = st.nextToken().toInt()

    grid = Array(H) { IntArray(W) { 0 } }
    for (y in 0 until H) {
        st = StringTokenizer(readLine()!!, " ")
        for (x in 0 until W) {
            grid[y][x] = st.nextToken().toInt()
        }
    }

    val moveList = readLine()!!.split(" ").map { it.toInt() }

    // 이동 수행
    for (move in moveList) {
        val nextY = curY + dy[move]
        val nextX = curX + dx[move]

        if (nextY in 0 until H && nextX in 0 until W) {
            // 주사위 회전
            rotateDice(move)

            // 이동
            curY = nextY
            curX = nextX

            // 수 복사
            if (grid[curY][curX] == 0) {
                grid[curY][curX] = dice[BOTTOM]
            } else {
                dice[BOTTOM] = grid[curY][curX]
                grid[curY][curX] = 0
            }

            sb.append("${dice[TOP]}\n")
        }
    }

    print(sb)
}

fun rotateDice(move: Int) {
    when (move) {
        EAST -> {
            val tmp = dice[EAST]
            dice[EAST] = dice[TOP]
            dice[TOP] = dice[WEST]
            dice[WEST] = dice[BOTTOM]
            dice[BOTTOM] = tmp
        }

        WEST -> {
            val tmp = dice[WEST]
            dice[WEST] = dice[TOP]
            dice[TOP] = dice[EAST]
            dice[EAST] = dice[BOTTOM]
            dice[BOTTOM] = tmp
        }

        NORTH -> {
            val tmp = dice[NORTH]
            dice[NORTH] = dice[TOP]
            dice[TOP] = dice[SOUTH]
            dice[SOUTH] = dice[BOTTOM]
            dice[BOTTOM] = tmp
        }

        SOUTH -> {
            val tmp = dice[SOUTH]
            dice[SOUTH] = dice[TOP]
            dice[TOP] = dice[NORTH]
            dice[NORTH] = dice[BOTTOM]
            dice[BOTTOM] = tmp
        }
    }
}