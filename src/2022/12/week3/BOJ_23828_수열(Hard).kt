import java.util.*

const val MAX_N = 1000
const val MAX_M = MAX_N
const val CACHE_EMPTY = -1L
const val MOD = 1000000007

var N = 0
var M = 0
val cache = Array(MAX_N + 1) { LongArray(MAX_N + 1) { CACHE_EMPTY } }
val counts = IntArray(100000 + 1) { 0 }
val uniqueSeqA = mutableListOf<Long>()
var sizeOfUniqueSeqA = 0

fun main() = with(System.`in`.bufferedReader()) {
    var st = StringTokenizer(readLine(), " ")
    N = st.nextToken().toInt()
    M = st.nextToken().toInt()

    val seqA = readLine().split(" ").map { it.toLong() }
    val set = HashSet<Long>()
    for (num in seqA) {
        set.add(num)
        counts[num.toInt()]++
    }
    for (num in set) {
        uniqueSeqA.add(num)
    }
    sizeOfUniqueSeqA = uniqueSeqA.size

    val answer = getSum(0, 0)

    println(answer)
}

fun getSum(startIdx: Int, pickedCnt: Int): Long {
    if (pickedCnt == M) {
        return 1L
    }
    if (startIdx == sizeOfUniqueSeqA) {
        return 0L
    }
    if (cache[startIdx][pickedCnt] != CACHE_EMPTY) {
        return cache[startIdx][pickedCnt]
    }


    // 안 쓰는 경우
    var ret = getSum(startIdx + 1, pickedCnt)

    // 쓰는 경우
    val num = uniqueSeqA[startIdx]
    ret = modAdd(ret, modMultiply(counts[num.toInt()].toLong(), modMultiply(num, getSum(startIdx + 1, pickedCnt + 1))))

    cache[startIdx][pickedCnt] = ret
    return ret
}

fun modAdd(a: Long, b: Long): Long {
    return (a + b) % MOD
}

fun modMultiply(a: Long, b: Long): Long {
    return (a * b) % MOD
}