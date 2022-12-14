import java.util.*
import kotlin.math.max

fun main() = with(System.`in`.bufferedReader()) {
    // 입력
    val N = readLine().toInt()

    val grid = Array(N + 1) { LongArray(N + 1) { 0L } }
    val visitPoints = Array(N + 1) { BooleanArray(N + 1) { false } }

    for (y in 1..N) {
        val st = StringTokenizer(readLine(), " ")
        for (x in 1..N) {
            grid[y][x] = st.nextToken().toLong()
        }
    }

    val P = readLine().toInt()
    repeat(P) {
        val (Y, X) = readLine().split(" ").map { it.toInt() }
        visitPoints[Y][X] = true
    }

    // 계산
    val answer = solve(N, grid, visitPoints)

    // 출력
    println(answer)

}

fun solve(N: Int, grid: Array<LongArray>, visitPoints: Array<BooleanArray>): Long {
    val dp = Array(N + 1) { Array(N + 1) { LongArray(2) { 0L } } }

    for (y in 1..N) {
        for (x in 1..N) {
            val maxVisited = max(dp[y - 1][x][1], dp[y][x - 1][1])
            val maxNotVisited = max(dp[y - 1][x][0], dp[y][x - 1][0])

            // 방문 목록에 있는 점이면
            if (visitPoints[y][x]) {
                dp[y][x][1] = grid[y][x] + max(maxVisited, maxNotVisited)
                dp[y][x][0] = grid[y][x] + max(maxVisited, maxNotVisited)
            }
            // 아니면
            else {
                dp[y][x][1] = if (maxVisited > 0L) (grid[y][x] + maxVisited) else 0L
                dp[y][x][0] = grid[y][x] + maxNotVisited
            }
        }
    }

    return dp[N][N][1]
}