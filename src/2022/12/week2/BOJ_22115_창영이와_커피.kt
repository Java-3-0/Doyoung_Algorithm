import kotlin.math.min

const val MAX_N = 100
const val MAX_K = 100000
const val INF = 987654321

fun main() = with(System.`in`.bufferedReader()) {
    // 입력
    val (N, K) = readLine().split(" ").map { it.toInt() }
    val coffeeList = readLine().split(" ").map { it.toInt() }

    // dp 배열 초기화
    val dp = IntArray(MAX_K + 1) { INF }
    dp[0] = 0

    // dp 수행
    for (coffee in coffeeList) {
        for (now in MAX_K downTo coffee) {
            val before = now - coffee
            if (dp[before] != INF) {
                dp[now] = min(dp[now], dp[before] + 1)
            }
        }
    }

    // 정답 출력
    println(if (dp[K] == INF) -1 else dp[K])
}