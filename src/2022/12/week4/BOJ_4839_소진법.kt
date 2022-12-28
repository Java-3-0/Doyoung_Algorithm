import java.util.*
import kotlin.math.sqrt

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    val primeList = getPrimes(10000)

    val baseList = mutableListOf<Long>()
    baseList.add(1L)
    var product = 1L
    for (prime in primeList) {
        product *= prime
        if (product in 0 .. Int.MAX_VALUE) {
            baseList.add(product)
        } else {
            break
        }
    }

    while (true) {
        val N = readLine()!!.toLong()

        if (N == 0L) {
            break
        }

        var cur = N

        val coefficients = LongArray(baseList.size) { 0L }
        for (idx in baseList.size - 1 downTo 0) {
            val base = baseList[idx]
            coefficients[idx] = cur / base
            cur %= base
        }

        sb.append("$N = ")
        for (i in coefficients.indices) {
            if (coefficients[i] > 0) {
                sb.append("${coefficients[i]}")

                for (p in 0 until i) {
                    sb.append("*${primeList[p]}")
                }
                sb.append(" + ")
            }
        }
        sb.setLength(sb.length - 3)
        sb.append("\n")
    }

    print(sb)
}

fun getPrimes(limit: Int): List<Long> {
    val isPrime = BooleanArray(limit + 1)
    Arrays.fill(isPrime, true)

    isPrime[0] = false
    isPrime[1] = false

    for (div in 2 .. sqrt(limit.toDouble()).toInt()) {
        if (isPrime[div]) {
            for (num in (div * div) .. limit step (div)) {
                isPrime[num] = false
            }
        }
    }

    val ret = mutableListOf<Long>()
    for (num in isPrime.indices) {
        if (isPrime[num]) {
            ret.add(num.toLong())
        }
    }

    return ret
}