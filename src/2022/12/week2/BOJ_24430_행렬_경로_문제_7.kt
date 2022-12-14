import java.util.*
import kotlin.math.max

data class State(var score: Int, var visitCnt: Int)

fun main() = with(System.`in`.bufferedReader()) {
    // 입력
    val N = readLine().toInt()

    val grid = Array(N + 1) { IntArray(N + 1) { 0 } }
    val isVisitPoint = Array(N + 1) { BooleanArray(N + 1) { false } }

    for (y in 1..N) {
        val st = StringTokenizer(readLine(), " ")
        for (x in 1..N) {
            grid[y][x] = st.nextToken().toInt()
        }
    }

    val P = readLine().toInt()
    repeat(P) {
        val (Y, X) = readLine().split(" ").map { it.toInt() }
        isVisitPoint[Y][X] = true
    }

    val answer = solve(N, grid, isVisitPoint)

    println("${answer.score} ${answer.visitCnt}")
}

fun solve(N: Int, grid: Array<IntArray>, isVisitPoint: Array<BooleanArray>): State {
    val dp = Array<Array<State>>(N + 1) { Array<State>(N + 1) { State(0, 0) } }

    for (y in 1..N) {
        for (x in 1..N) {
            if (dp[y - 1][x].score < dp[y][x - 1].score) {
                dp[y][x].score = dp[y][x - 1].score
                dp[y][x].visitCnt = dp[y][x - 1].visitCnt
            } else if (dp[y - 1][x].score > dp[y][x - 1].score) {
                dp[y][x].score = dp[y - 1][x].score
                dp[y][x].visitCnt = dp[y - 1][x].visitCnt
            } else {
                dp[y][x].score = dp[y - 1][x].score
                dp[y][x].visitCnt = max(dp[y][x - 1].visitCnt, dp[y - 1][x].visitCnt)
            }

            dp[y][x].score += grid[y][x]
            if (isVisitPoint[y][x]) {
                dp[y][x].visitCnt++
            }
        }
    }

    return dp[N][N]

}