import java.util.*

const val MAX_H = 1000
const val MAX_W = 1000
const val WATER = '*'
const val EMPTY = '.'
const val ROCK = 'X'
const val DESTINATION = 'D'
const val START = 'S'

val dy = intArrayOf(-1, 0, 1, 0)
val dx = intArrayOf(0, 1, 0, -1)

var H = 0
var W = 0
val grid = Array(MAX_H) { CharArray(MAX_W) }
val visitCheck = Array(MAX_H) { BooleanArray(MAX_W) { false } }
val q = LinkedList<Position>()

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

    fun getGridVal(): Char {
        return grid[y][x]
    }

    fun setGridVal(v: Char) {
        grid[y][x] = v
    }
}

fun main(): Unit = with(System.`in`.bufferedReader()) {
    var st = StringTokenizer(readLine()!!, " ")
    H = st.nextToken().toInt()
    W = st.nextToken().toInt()

    var startY = -1
    var startX = -1
    for (y in 0 until H) {
        val line = readLine()!!
        for (x in 0 until W) {
            val c = line[x]
            if (c == START) {
                startY = y
                startX = x
                grid[y][x] = EMPTY
            } else {
                grid[y][x] = c
            }
        }
    }

    val answer = bfs(Position(startY, startX))

    println(if (answer == -1) "KAKTUS" else "$answer")
}

fun bfs(start: Position): Int {
    start.visit()
    q.offer(start)

    var depth = 0
    while (q.isNotEmpty()) {
        expandWaterArea()

        val size = q.size
        repeat(size) {
            val cur = q.poll()!!
            if (cur.getGridVal() == DESTINATION) {
                return depth
            }

            for (dir in dy.indices) {
                val next = cur.getNextPosition(dir)
                if (next.isInRange() && !next.isVisited() && next.getGridVal() != WATER && next.getGridVal() != ROCK) {
                    next.visit()
                    q.offer(next)
                }
            }
        }

        depth++
    }

    return -1
}

fun expandWaterArea() {
    // 물과 인접한 빈 칸들을 set에 담기
    val set = HashSet<Position>()
    for (y in 0 until H) {
        for (x in 0 until W) {
            val pos = Position(y, x)
            if (pos.getGridVal() == WATER) {
                for (dir in dy.indices) {
                    val adjPos = pos.getNextPosition(dir)
                    if (adjPos.isInRange() && adjPos.getGridVal() == EMPTY) {
                        set.add(adjPos)
                    }
                }
            }
        }
    }

    // set 에 담긴 것들에 대해 grid 갱신
    for (position in set) {
        position.setGridVal(WATER)
    }
}