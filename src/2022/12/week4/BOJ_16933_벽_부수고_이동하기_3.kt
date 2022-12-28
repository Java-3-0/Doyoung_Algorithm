import java.util.*


const val MAX_H = 1000
const val MAX_W = 1000
const val MAX_K = 10

val dy = intArrayOf(0, 1, 0, -1)
val dx = intArrayOf(1, 0, -1, 0)
const val INF = 987654321
const val NOT_VISITED = INF
const val EMPTY = 0
const val WALL = 1
const val NIGHT = 0
const val DAY = 1

var H = 0
var W = 0
var K = 0

var grid = Array(MAX_H) { IntArray(MAX_W) }
var visitCheck = Array(MAX_H) {
    Array(MAX_W) {
        IntArray(2) { NOT_VISITED }
    }
}

data class State(val y: Int, val x: Int, val crushWallCnt: Int, val isDay: Int) {
    fun isVisited(): Boolean {
        return visitCheck[y][x][isDay] <= crushWallCnt
    }

    fun visit() {
        visitCheck[y][x][isDay] = crushWallCnt
    }
}

fun main(): Unit = with(System.`in`.bufferedReader()) {
    var st = StringTokenizer(readLine()!!, " ")
    H = st.nextToken().toInt()
    W = st.nextToken().toInt()
    K = st.nextToken().toInt()

    // 그리드 입력
    for (y in 0 until H) {
        val line = readLine()!!
        for (x in 0 until W) {
            grid[y][x] = line[x].minus('0')
        }
    }

    val answer = bfs()

    println(answer)
}

fun bfs(): Int {
    val start = State(0, 0, 0, DAY)
    val q = LinkedList<State>()

    start.visit()
    q.offer(start)

    var depth = 1 // 시작 칸도 포함한 거리

    // bfs 수행
    while (q.isNotEmpty()) {
        val size = q.size
        repeat(size) {
            val cur = q.poll()!!

            if (cur.y == H - 1 && cur.x == W - 1) {
                return depth
            }

            // 그냥 제자리에 있는 경우
            val stay = State(cur.y, cur.x, cur.crushWallCnt, 1 - cur.isDay)
            if (!stay.isVisited()) {
                stay.visit()
                q.offer(stay)
            }

            for (dir in dy.indices) {
                val nextY = cur.y + dy[dir]
                val nextX = cur.x + dx[dir]

                // 범위 체크
                if (nextY in 0 until H && nextX in 0 until W) {
                    // 빈 칸이면 그냥 이동 가능
                    if (grid[nextY][nextX] == EMPTY) {
                        val next = State(nextY, nextX, cur.crushWallCnt, 1 - cur.isDay)
                        if (!next.isVisited()) {
                            next.visit()
                            q.offer(next)
                        }
                    } else {
                        // 낮에만 벽을 부술 수 있음
                        if (cur.isDay == DAY) {
                            val next = State(nextY, nextX, 1 + cur.crushWallCnt, 1 - cur.isDay)
                            if (next.crushWallCnt <= K && !next.isVisited()) {
                                next.visit()
                                q.offer(next)
                            }
                        }
                    }
                }
            }
        }

        depth++
    }

    return -1
}
