import java.util.*

const val MAX_H = 20
const val MAX_W = 20
const val MAX_FLOORS = 50
const val EMPTY = '.'
const val WALL = '#'

const val MAX_GROUPS = (MAX_H * MAX_W / 2) * MAX_FLOORS
const val MAX_DOMAIN = MAX_GROUPS
const val MAX_RANGE = MAX_GROUPS

const val NO_GROUP = -2
const val NOT_MATCHED = -1

val dy = intArrayOf(-1, 0, 1, 0)
val dx = intArrayOf(0, 1, 0, -1)

var H = 0
var W = 0
var K = 0
var groupCnt = 0
val grid = Array(MAX_FLOORS + 1) { Array(MAX_H + 1) { CharArray(MAX_W + 1) } }
val groupNumbers = Array(MAX_FLOORS + 1) { Array(MAX_H + 1) { IntArray(MAX_W + 1) { NO_GROUP } } }
val visitCheck = Array(MAX_FLOORS + 1) { Array(MAX_H + 1) { BooleanArray(MAX_W + 1) { false } } }

var DOMAIN = MAX_DOMAIN
var RANGE = MAX_RANGE

val adjSet = Array(MAX_GROUPS + 1) { hashSetOf<Int>() }
val isChecked = BooleanArray(MAX_RANGE + 1) { false }
val isMatchedTo = IntArray(MAX_RANGE + 1) { NOT_MATCHED }

data class Position(val z: Int, val y: Int, val x: Int) {
    fun isVisited(): Boolean {
        return visitCheck[z][y][x]
    }

    fun visit() {
        visitCheck[z][y][x] = true
    }

    fun isInRange(): Boolean {
        return z in 0 until K && y in 0 until H && x in 0 until W
    }

    fun getNextPosition(dir: Int): Position {
        return Position(z, y + dy[dir], x + dx[dir])
    }

    fun getGridVal(): Char {
        return grid[z][y][x]
    }

    fun setGridVal(c: Char) {
        grid[z][y][x] = c
    }

    fun getGroupNum(): Int {
        return groupNumbers[z][y][x]
    }

    fun setGroupNum(n: Int) {
        groupNumbers[z][y][x] = n
    }
}

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    val tests = readLine()!!.trim().toInt()
    for (tc in 1 .. tests) {
        // 메모리 초기화
        initMemory()

        // 그리드 크기 입력
        val st = StringTokenizer(readLine()!!, " ")
        H = st.nextToken().toInt()
        W = st.nextToken().toInt()
        K = st.nextToken().toInt()

        // 그리드 입력
        for (z in 0 until K) {
            for (y in 0 until H) {
                val line = readLine()!!
                for (x in 0 until W) {
                    grid[z][y][x] = line[x]
                }
            }
        }

        // 그룹핑
        for (z in 0 until K) {
            for (y in 0 until H) {
                for (x in 0 until W) {
                    val position = Position(z, y, x)
                    if (!position.isVisited() && position.getGridVal() == EMPTY) {
                        setGroupNumbersByBFS(position)
                    }
                }
            }
        }

        DOMAIN = groupCnt
        RANGE = groupCnt

        // 함께할 수 없는 그룹끼리 간선 연결
        for (z in 1 until K) {
            for (y in 0 until H) {
                for (x in 0 until W) {
                    val group1 = groupNumbers[z][y][x]
                    val group2 = groupNumbers[z - 1][y][x]
                    if (group1 != NO_GROUP && group2 != NO_GROUP) {
                        if (z % 2 == 0) {
                            adjSet[group1].add(group2)
                        } else {
                            adjSet[group2].add(group1)
                        }
                    }
                }
            }
        }

        // 최대 독립 집합 = 정점 수 - 최대 매칭 수
        val maxMatches = getMaxBipartiteMatches()
        val answer = groupCnt - maxMatches

        sb.append("Case #$tc: $answer\n")
    }

    print(sb)

}

/** bfs 돌면서 그룹 번호 매기기 */
fun setGroupNumbersByBFS(start: Position) {
    groupCnt++

    val q = LinkedList<Position>()
    q.offer(start)

    while (q.isNotEmpty()) {
        val cur = q.poll()
        cur.setGroupNum(groupCnt)

        for (dir in dy.indices) {
            val next = cur.getNextPosition(dir)
            if (next.isInRange() && !next.isVisited() && next.getGridVal() == EMPTY) {
                next.visit()
                q.offer(next)
            }
        }
    }
}

/** 메모리 초기화 */
fun initMemory() {
    for (i in grid.indices) {
        for (j in grid[i].indices) {
            Arrays.fill(grid[i][j], EMPTY)
        }
    }
    for (i in groupNumbers.indices) {
        for (j in groupNumbers[i].indices) {
            Arrays.fill(groupNumbers[i][j], NO_GROUP)
        }
    }
    for (i in visitCheck.indices) {
        for (j in visitCheck[i].indices) {
            Arrays.fill(visitCheck[i][j], false)
        }
    }
    for (set in adjSet) {
        set.clear()
    }

    Arrays.fill(isChecked, false)
    Arrays.fill(isMatchedTo, NOT_MATCHED)
    groupCnt = 0
}

/** 이분매칭 최대 매칭 수 리턴 */
fun getMaxBipartiteMatches(): Int {
    var ret = 0

    for (from in 1 .. DOMAIN) {
        Arrays.fill(isChecked, false)
        if (dfs(from)) {
            ret++
        }
    }

    return ret
}


/** dfs 를 수행하면서 매칭 시도  */
fun dfs(from: Int): Boolean {
    for (to in adjSet[from]) {
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