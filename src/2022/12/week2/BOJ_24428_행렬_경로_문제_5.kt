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
    // dp[y][x][i]는 i개 이상 방문하고 온 (y, x) 위치에서의 최대값
    val dp = Array(N + 1) { Array(N + 1) { LongArray(4) { -1L } } }
    for (y in 0 .. N) {
        dp[y][0][0] = 0
    }
    for (x in 0 .. N) {
        dp[x][0][0] = 0
    }

    for (y in 1..N) {
        for (x in 1..N) {
            // 이전 칸들의 각 방문 원소 개수마다 최대값을 구함
            val maxArr = Array<Long>(4) { i -> (max(dp[y - 1][x][i], dp[y][x - 1][i])) }

            // 방문 목록에 있는 점이면
            if (visitPoints[y][x]) {
                for (i in 1..3) {
                    if (maxArr[i - 1] != -1L) {
                        dp[y][x][i] = grid[y][x] + maxArr[i - 1]
                    }
                }
                dp[y][x][0] = grid[y][x] + maxArr[0]
            }
            // 아니면
            else {
                for (i in 0..3) {
                    if (maxArr[i] != -1L) {
                        dp[y][x][i] = grid[y][x] + maxArr[i]
                    }
                }
            }
        }
    }

    return dp[N][N][3];
}