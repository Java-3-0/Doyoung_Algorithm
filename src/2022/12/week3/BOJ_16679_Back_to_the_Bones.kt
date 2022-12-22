import java.util.*

const val MAX_N = 20
const val MAX_K = 6 * MAX_N

val diceList = mutableListOf<Dice>()
val prefixSums = IntArray(MAX_N + 1) { 0 }
val dp = Array(MAX_N + 1) { LongArray(MAX_K + 1) { 0L } }
val isRerolled = BooleanArray(MAX_N + 1) {false}

var N = 0
var K = 0

class Dice(val idx: Int, val num: Int) : Comparable<Dice> {
    override fun compareTo(other: Dice): Int {
        return this.num.compareTo(other.num)
    }
}


fun main() = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()
    val tests = readLine().toInt()

    repeat(tests) {
        // 메모리 초기화
        diceList.clear()
        Arrays.fill(prefixSums, 0)
        for (i in dp.indices) {
            Arrays.fill(dp[i], 0)
        }
        Arrays.fill(isRerolled, false)

        // N, K 입력
        var st = StringTokenizer(readLine(), " ")
        N = st.nextToken().toInt()
        K = st.nextToken().toInt()

        // 주사위 입력
        st = StringTokenizer(readLine(), " ")
        diceList.add(Dice(-1, -1))
        for (idx in 1..N) {
            val num = st.nextToken().toInt()
            diceList.add(Dice(idx, num))
        }

        // 주사위 오름차순 정렬
        diceList.sort()

        // 누적합 계산
        var sum = 0
        for (i in 1..N) {
            sum += diceList[i].num
            prefixSums[i] = sum
        }

        // dp 수행
        dp[0][prefixSums[N] - prefixSums[0]] = 1
        for (cnt in 1..N) {
            for (k in 0..MAX_K) {
                for (dice in 1..6) {
                    if (k - diceList[cnt].num + dice in 0..MAX_K) {
                        dp[cnt][k - diceList[cnt].num + dice] += dp[cnt - 1][k]
                    }
                }
            }
        }

        // dp 배열에서 row 마다 합산
        val numberOfWays = LongArray(MAX_N + 1) { 0L }
        for (reRoll in 0 .. N) {
            var cnt = 0L
            for (k in K .. MAX_K) {
                cnt += dp[reRoll][k]
            }

            numberOfWays[reRoll] = cnt * getPower(6L, N - reRoll)
        }

        // 정답 계산
        val bestNumberOfWays = numberOfWays.max()
        val bestReRoll = numberOfWays.indexOf(bestNumberOfWays)

        // 작은 순서대로 bestReRoll 개의 주사위 세팅
        for (i in 1 .. bestReRoll) {
            isRerolled[diceList[i].idx] = true
        }

        // 출력
        sb.append("$bestNumberOfWays\n")
        for (i in 1 .. N) {
            sb.append(if (isRerolled[i]) "1" else "0").append(" ")
        }
        sb.append("\n")
    }

    print(sb)
}

fun getPower(base: Long, exponent: Int): Long {
    if (exponent == 0) {
        return 1L
    }

    val half = getPower(base, exponent / 2)
    return if (exponent % 2 == 0) {
        half * half
    } else {
        half * half * base
    }

}