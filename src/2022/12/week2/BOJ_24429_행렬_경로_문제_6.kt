import java.util.*
import kotlin.math.max

data class Position(val y: Int, val x: Int) : Comparable<Position> {
    override fun compareTo(other: Position): Int {
        return compareValuesBy(this, other, { it.y }, { it.x })
    }
}


fun main() = with(System.`in`.bufferedReader()) {
    // 입력
    val N = readLine().toInt()

    val grid = Array(N + 1) { LongArray(N + 1) { 0L } }

    for (y in 1..N) {
        val st = StringTokenizer(readLine(), " ")
        for (x in 1..N) {
            grid[y][x] = st.nextToken().toLong()
        }
    }

    val visitPointList = mutableListOf<Position>()
    val P = readLine().toInt()
    repeat(P) {
        val (Y, X) = readLine().split(" ").map { it.toInt() }
        visitPointList.add(Position(Y, X))
    }
    visitPointList.add(Position(N, N))
    visitPointList.sort()

    // 계산
    val answer = solve(N, grid, visitPointList)

    // 출력
    println(answer)

}

fun solve(N: Int, grid: Array<LongArray>, visitPointList: MutableList<Position>): Long {
    val dp = Array(N + 1) { LongArray(N + 1) { 0L } }

    var nowY = 1
    var nowX = 1
    for (visitPoint in visitPointList) {
        if (visitPoint.y < nowY || visitPoint.x < nowX) {
            return -1L
        }

        for (y in nowY..visitPoint.y) {
            for (x in nowX..visitPoint.x) {
                dp[y][x] = grid[y][x] + max(dp[y - 1][x], dp[y][x - 1])
            }
        }

        nowY = visitPoint.y
        nowX = visitPoint.x
    }

    return dp[N][N];
}