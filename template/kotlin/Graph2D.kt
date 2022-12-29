const val MAX_H = 1000
const val MAX_W = 1000

val dy = intArrayOf(-1, 0, 1, 0)
val dx = intArrayOf(0, 1, 0, -1)

var H = 0
var W = 0
val grid = Array(MAX_H) { IntArray(MAX_W) { 0 } }
val visitCheck = Array(MAX_H) { BooleanArray(MAX_W) { false } }

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