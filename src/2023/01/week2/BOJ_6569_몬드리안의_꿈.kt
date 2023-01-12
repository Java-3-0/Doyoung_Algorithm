import java.util.*


const val MAX_H = 14
const val MAX_W = 14
const val MAX_BITMASK = (1 shl MAX_W) - 1
const val CACHE_EMPTY = -1L

var H = 0
var W = 0
var cache = Array(MAX_H * MAX_W + 1) { LongArray(MAX_BITMASK + 1) { CACHE_EMPTY } }

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    while (true) {
        initCache()

        var st = StringTokenizer(readLine()!!, " ")
        H = st.nextToken().toInt()
        W = st.nextToken().toInt()
        if (H == 0 && W == 0) {
            break
        }

        val answer = countWaysToFill(0, 0)

        sb.append("$answer\n")
    }

    print(sb)
}

fun countWaysToFill(idx: Int, used: Int): Long {
    if (idx == H * W) {
        return 1L
    }

    if (cache[idx][used] != CACHE_EMPTY) {
        return cache[idx][used]
    }

    var ret = 0L
    // 이미 묶인 경우
    if (used and 1 != 0) {
        ret = countWaysToFill(idx + 1, used shr 1)
    }
    // 새로 묶는 경우
    else {
        val y = idx / W
        val x = idx % W

        // 아래랑 묶는 경우
        if (y + 1 < H) {
            ret += countWaysToFill(idx + 1, used shr 1 or (1 shl W - 1))
        }

        // 오른쪽과 묶는 경우
        if (x + 1 < W && used and (1 shl 1) == 0) {
            ret += countWaysToFill(idx + 1, used shr 1 or 1)
        }
    }

    cache[idx][used] = ret
    return ret
}

fun initCache() {
    for (arr in cache) {
        Arrays.fill(arr, CACHE_EMPTY)
    }
}