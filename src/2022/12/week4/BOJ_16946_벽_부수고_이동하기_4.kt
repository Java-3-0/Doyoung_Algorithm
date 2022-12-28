import java.util.*

const val MAX_H = 1000
const val MAX_W = 1000
const val WALL = 1
const val EMPTY = -1

val dy = intArrayOf(0, 1, 0, -1)
val dx = intArrayOf(1, 0, -1, 0)

var H = 0
var W = 0
val grid = Array(MAX_H) { IntArray(MAX_W) { 0 } }
val visitCheck = Array(MAX_H) { BooleanArray(MAX_W) { false } }
val mapGroupSize = HashMap<Int, Int>()
var groupNum = -1

data class Position(val y: Int, val x: Int) {
    fun isVisited(): Boolean {
        return visitCheck[y][x]
    }

    fun visit() {
        visitCheck[y][x] = true
    }

    fun isInRange(): Boolean {
        return y in 0 until H && x in 0 until W
    }

    fun getNextPosition(dir: Int): Position {
        return Position(y + dy[dir], x + dx[dir])
    }

    fun getGridVal(): Int {
        return grid[y][x]
    }

    fun setGridVal(v: Int) {
        grid[y][x] = v
    }
}

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    var st = StringTokenizer(readLine()!!, " ")
    H = st.nextToken().toInt()
    W = st.nextToken().toInt()

    for (y in 0 until H) {
        val line = readLine()!!
        for (x in 0 until W) {
            grid[y][x] = line[x].minus('0')
        }
    }

    // 그룹마다 크기를 계산
    for (y in 0 until H) {
        for (x in 0 until W) {
            if (grid[y][x] != WALL && !visitCheck[y][x]) {
                bfs(Position(y, x))
            }
        }
    }

    // 사방으로 인접한 그룹의 크기들을 합산
    for (y in 0 until H) {
        for (x in 0 until W) {
            val cur = Position(y, x)
            if (cur.getGridVal() == WALL) {
                val groupSet = HashSet<Int>()
                for (dir in dy.indices) {
                    val next = cur.getNextPosition(dir)
                    if (next.isInRange() && next.getGridVal() < 0) {
                        groupSet.add(next.getGridVal())
                    }
                }

                var answer = 1
                for (group in groupSet) {
                    answer += mapGroupSize[group]!!
                }
                answer %= 10
                sb.append(answer)
            } else {
                sb.append(0)
            }
        }
        sb.append("\n")
    }

    print(sb)
}

fun bfs(start: Position) {
    val q = LinkedList<Position>()

    start.visit()
    q.offer(start)

    var cnt = 0
    while (q.isNotEmpty()) {
        val cur = q.poll()!!
        cur.setGridVal(groupNum)
        cnt++

        for (dir in dy.indices) {
            val next = cur.getNextPosition(dir)
            if (next.isInRange() && next.getGridVal() != WALL && !next.isVisited()) {
                next.visit()
                q.offer(next)
            }
        }
    }

    mapGroupSize[groupNum] = cnt
    groupNum--
}
