import java.util.StringTokenizer

const val MOD = 1000000007

fun main() = with(System.`in`.bufferedReader()) {
    val N = readLine().toInt()

    val seq = LongArray(N + 1)
    val st = StringTokenizer(readLine(), " ")
    for (i in 1 .. N) {
        seq[i] = st.nextToken().toLong()
    }

    val prefixSums = LongArray(N + 1)
    var sum = 0L
    for (i in 1 .. N) {
        sum += seq[i]
        prefixSums[i] = sum
    }

    var answer = 0L
    for (i in 1 until N) {
        // i + 1에서 N까지
        val mult = modMultiply(seq[i], prefixSums[N] - prefixSums[i])
        answer = modAdd(answer, mult)
    }

    println(answer)
}

fun modAdd(a: Long, b: Long): Long {
    return (a + b) % MOD
}

fun modMultiply(a: Long, b: Long): Long {
    return (a * b) % MOD
}