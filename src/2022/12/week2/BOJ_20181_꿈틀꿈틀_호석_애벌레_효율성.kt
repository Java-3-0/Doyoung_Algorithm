import java.util.*
import kotlin.math.max

const val MAX_N = 100000
const val MAX_K = 100000000
const val CACHE_EMPTY = -1L

var N = 0
var K = 0
val cache = LongArray(MAX_N + 1) { CACHE_EMPTY }
val foodList = mutableListOf<Long>()

val eatUntil = IntArray(MAX_N + 1)
val eatAmount = LongArray(MAX_N + 1)

fun main() = with(System.`in`.bufferedReader()) {
    // 입력
    var st = StringTokenizer(readLine(), " ")
    N = st.nextToken().toInt()
    K = st.nextToken().toInt()

    st = StringTokenizer(readLine(), " ")
    repeat(N) {
        foodList.add(st.nextToken().toLong())
    }

    // 각 지점에서 시작할 경우 어디까지 먹어야 하는지, 또 그 때 얼마나 먹게 되는지 미리 계산
    Arrays.fill(eatUntil, N - 1)
    var left = 0
    var right = 0
    var sum = foodList[0]
    while (left < N) {
        while (right < left) {
            right++
            if (right < N) {
                sum += foodList[right]
            }
        }

        // 오른쪽 끝에 도달한 경우
        if (right >= N) {
            eatAmount[left] = sum

            sum -= foodList[left]
            left++
        }

        // 더 먹어야 하는 경우
        else if (sum < K) {
            right++
            if (right < N) {
                sum += foodList[right]
            }
        }

        // 더 못 먹는 경우
        else {
            eatAmount[left] = sum
            eatUntil[left] = right

            sum -= foodList[left]
            left++
        }
    }

    // 정답 계산
    val answer = getMaxEnergy(0)

    // 출력
    println(answer)
}

fun getMaxEnergy(startIdx: Int): Long {
    if (startIdx == N) {
        return 0L
    }

    if (cache[startIdx] != CACHE_EMPTY) {
        return cache[startIdx]
    }

    // 안 먹는 경우
    var ret = getMaxEnergy(startIdx + 1)

    // 먹는 경우
    var satisfaction = eatAmount[startIdx]
    var endPointIdx = eatUntil[startIdx]
    if (satisfaction >= K) {
        ret = max(ret, satisfaction - K + getMaxEnergy(endPointIdx + 1))
    }

    cache[startIdx] = ret
    return ret
}