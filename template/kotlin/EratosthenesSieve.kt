import java.util.*
import kotlin.math.sqrt

fun getPrimes(limit: Int): List<Int> {
    val isPrime = BooleanArray(limit + 1)
    Arrays.fill(isPrime, true)

    isPrime[0] = false
    isPrime[1] = false

    for (div in 2..sqrt(limit.toDouble()).toInt()) {
        if (isPrime[div]) {
            for (num in (div * div)..limit step (div)) {
                isPrime[num] = false
            }
        }
    }

    val ret = mutableListOf<Int>()
    for (num in isPrime.indices) {
        if (isPrime[num]) {
            ret.add(num)
        }
    }

    return ret
}