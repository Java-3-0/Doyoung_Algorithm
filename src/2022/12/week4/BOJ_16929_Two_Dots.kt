import java.util.*

val dy = intArrayOf(-1, 1, 0, 0)
val dx = intArrayOf(0, 0, -1, 1)

var H = 0
var W = 0
var grid = arrayOf(charArrayOf())
var isVisited = arrayOf(booleanArrayOf())

fun main() = with(System.`in`.bufferedReader()) {
    var st = StringTokenizer(readLine()!!, " ")
    H = st.nextToken().toInt()
    W = st.nextToken().toInt()

    grid = Array(H) { CharArray(W) }
    isVisited = Array(H) { BooleanArray(W) }

    for (y in 0 until H) {
        val line = readLine()!!
        for (x in 0 until W) {
            grid[y][x] = line[x]
        }
    }

    var hasCycle = false
    loop@ for (y in 0 until H) {
        for (x in 0 until W) {
            isVisited.forEach { row -> Arrays.fill(row, false) }

            isVisited[y][x] = true
            if (dfs(y, x, y, x, 0)) {
                hasCycle = true
                break@loop
            }
        }
    }

    println(if (hasCycle) "Yes" else "No")
}

fun dfs(curY: Int, curX: Int, startY: Int, startX: Int, depth: Int): Boolean {
    for (dir in 0 until 4) {
        val nextY = curY + dy[dir]
        val nextX = curX + dx[dir]

        // 범위 안이고, 이동할 수 있는 경우
        if (nextY in 0 until H && nextX in 0 until W && grid[curY][curX] == grid[nextY][nextX]) {
            // 아직 방문하지 않은 곳이면 방문
            if (!isVisited[nextY][nextX]) {
                isVisited[nextY][nextX] = true
                if (dfs(nextY, nextX, startY, startX, depth + 1)) {
                    return true
                }
            }
            // 이미 방문했던 곳인 경우, 시작 지점인지 확인
            else {
                if (nextY == startY && nextX == startX && depth + 1 >= 4) {
                    return true
                }
            }
        }
    }

    return false
}
