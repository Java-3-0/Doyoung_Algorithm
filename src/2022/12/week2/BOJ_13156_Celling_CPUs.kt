import java.util.*
import kotlin.math.max

const val MAX_CPUS = 100
const val MAX_MERCHANTS = 100
const val CACHE_EMPTY = -1

var CPUS = 0
var MERCHANTS = 0
var priceArr = Array(MAX_MERCHANTS + 1) { IntArray(MAX_CPUS + 1) { 0 } }
val cache = Array(MAX_MERCHANTS + 1) { IntArray(MAX_CPUS + 1) { CACHE_EMPTY } }

fun main() = with(System.`in`.bufferedReader()) {
    // 입력
    var st = StringTokenizer(readLine(), " ")
    CPUS = st.nextToken().toInt()
    MERCHANTS = st.nextToken().toInt()

    for (y in 0 until MERCHANTS) {
        st = StringTokenizer(readLine(), " ")
        for (x in 0 until CPUS) {
            priceArr[y][x] = st.nextToken().toInt()
        }
    }

    // 정답 계산
    val answer = getMaxProfit(0, CPUS)

    // 출력
    println(answer)
}

fun getMaxProfit(merchantIdx: Int, remainingCPU: Int): Int {
    // base case
    if (merchantIdx == MERCHANTS) {
        return 0
    }

    // memoization
    if (cache[merchantIdx][remainingCPU] != CACHE_EMPTY) {
        return cache[merchantIdx][remainingCPU]
    }

    // 새로 계산
    var maxProfit = 0
    for (sell in 0..remainingCPU) {
        val price = if (sell == 0) 0 else priceArr[merchantIdx][sell - 1]
        maxProfit = max(maxProfit, price + getMaxProfit(merchantIdx + 1, remainingCPU - sell))
    }

    cache[merchantIdx][remainingCPU] = maxProfit
    return maxProfit
}