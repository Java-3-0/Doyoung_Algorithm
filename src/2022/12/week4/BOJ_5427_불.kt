import java.util.*

const val MAX_H = 1000
const val MAX_W = 1000
const val EMPTY = '.'
const val WALL = '#'
const val FIRE = '*'
const val START = '@'

val dy = intArrayOf(-1, 0, 1, 0)
val dx = intArrayOf(0, 1, 0, -1)

var H = 0
var W = 0
val grid = Array(MAX_H) { CharArray(MAX_W) }
val visitCheck = Array(MAX_H) { BooleanArray(MAX_W) { false } }
val q = LinkedList<Position>()
var fireBorderSet = HashSet<Position>()

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
    val sb = StringBuilder()

    val tests = readLine()!!.toInt()
    repeat(tests) {
        initMemory()

        var st = StringTokenizer(readLine()!!, " ")
        W = st.nextToken().toInt()
        H = st.nextToken().toInt()

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

        sb.append(if (answer == -1) "IMPOSSIBLE" else "$answer").append("\n")
    }

    print(sb)
}

fun initMemory() {
    for (i in visitCheck.indices) {
        Arrays.fill(visitCheck[i], false)
    }
    q.clear()
    fireBorderSet.clear()
}

fun bfs(start: Position): Int {
    start.visit()
    q.offer(start)

    findInitialFireBorders()

    var depth = 0
    while (q.isNotEmpty()) {
        expandFireArea()

        val size = q.size
        repeat(size) {
            val cur = q.poll()!!
            if (cur.y == 0 || cur.y == H - 1 || cur.x == 0 || cur.x == W - 1) {
                return depth + 1
            }

            for (dir in dy.indices) {
                val next = cur.getNextPosition(dir)
                if (next.isInRange() && !next.isVisited() && next.getGridVal() != FIRE && next.getGridVal() != WALL) {
                    next.visit()
                    q.offer(next)
                }
            }
        }

        depth++
    }

    return -1
}

fun findInitialFireBorders() {
    for (y in 0 until H) {
        for (x in 0 until W) {
            val pos = Position(y, x)
            if (pos.getGridVal() == FIRE) {
                for (dir in dy.indices) {
                    val next = pos.getNextPosition(dir)
                    if (next.isInRange() && next.getGridVal() == EMPTY) {
                        fireBorderSet.add(pos)
                    }
                }
            }
        }
    }
}

fun expandFireArea() {
    val nextFireBorderSet = HashSet<Position>()

    for (pos in fireBorderSet) {
        for (dir in dy.indices) {
            val next = pos.getNextPosition(dir)
            if (next.isInRange() && next.getGridVal() == EMPTY) {
                nextFireBorderSet.add(next)
            }
        }
    }

    // list 에 담긴 것들에 대해 grid 갱신
    for (position in nextFireBorderSet) {
        position.setGridVal(FIRE)
    }

    fireBorderSet = nextFireBorderSet
}