import java.util.*

const val MAX_N = 10
const val TYPES = 3
const val KNIGHT = 0
const val BISHOP = 1
const val ROOK = 2

var N = 0
val grid = Array(MAX_N) { IntArray(MAX_N) { 0 } }
val visitCheck = Array(MAX_N) { Array(MAX_N) { Array(TYPES) { BooleanArray(MAX_N * MAX_N + 1) } } }
val q = LinkedList<State>()

val dyKnight = intArrayOf(2, 2, 1, 1, -2, -2, -1, -1)
val dxKnight = intArrayOf(1, -1, 2, -2, 1, -1, 2, -2)
val dyBishop = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -2, -3, -4, -5, -6, -7, -8, -9, -1, -2, -3, -4, -5, -6, -7, -8, -9)
val dxBishop = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -2, -3, -4, -5, -6, -7, -8, -9, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -2, -3, -4, -5, -6, -7, -8, -9)
val dyRook = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -2, -3, -4, -5, -6, -7, -8, -9)
val dxRook = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -2, -3, -4, -5, -6, -7, -8, -9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)

val dyArr = arrayOf(dyKnight, dyBishop, dyRook)
val dxArr = arrayOf(dxKnight, dxBishop, dxRook)

data class State(val y: Int, val x: Int, val pieceType: Int, val lastNum: Int) {
    fun isVisited(): Boolean {
        return visitCheck[y][x][pieceType][lastNum]
    }

    fun visit() {
        visitCheck[y][x][pieceType][lastNum] = true
    }
}

fun main(): Unit = with(System.`in`.bufferedReader()) {
    // 입력
    N = readLine()!!.toInt()
    for (y in 0 until N) {
        val st = StringTokenizer(readLine()!!, " ")
        for (x in 0 until N) {
            grid[y][x] = st.nextToken().toInt()
        }
    }

    val answer = bfs()

    println(answer)
}

fun findOne(): Pair<Int, Int> {
    for (y in 0 until N) {
        for (x in 0 until N) {
            if (grid[y][x] == 1) {
                return Pair(y, x)
            }
        }
    }
    return Pair(-1, -1)
}

fun bfs(): Int {
    // 1이 있는 위치에 3가지 타입의 말을 놓고 시작
    val startPosition = findOne()
    val startY = startPosition.first
    val startX = startPosition.second

    for (pieceType in 0 until TYPES) {
        val startState = State(startY, startX, pieceType, 1)
        startState.visit()
        q.offer(startState)
    }

    // bfs 수행
    var depth = 0
    while (q.isNotEmpty()) {
        val size = q.size
        repeat(size) {
            val curState = q.poll()!!

            if (curState.lastNum == N * N) {
                return depth
            }

            // 말 타입을 바꾸는 경우
            for (i in 1 .. 2) {
                val nextState = State(curState.y, curState.x, (curState.pieceType + i) % TYPES, curState.lastNum)
                if (!nextState.isVisited()) {
                    nextState.visit()
                    q.offer(nextState)
                }
            }

            // 이동하는 경우
            val dy = dyArr[curState.pieceType]
            val dx = dxArr[curState.pieceType]
            for (dir in dy.indices) {
                val nextY = curState.y + dy[dir]
                val nextX = curState.x + dx[dir]
                if (nextY in 0 until N && nextX in 0 until N) {
                    val nextLastNum = if (grid[nextY][nextX] == curState.lastNum + 1) (curState.lastNum + 1) else (curState.lastNum)
                    val nextState = State(nextY, nextX, curState.pieceType, nextLastNum)
                    if (!nextState.isVisited()) {
                        nextState.visit()
                        q.offer(nextState)
                    }
                }
            }
        }

        depth++
    }

    return -1
}