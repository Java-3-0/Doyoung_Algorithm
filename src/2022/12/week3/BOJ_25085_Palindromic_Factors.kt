import kotlin.math.roundToLong
import kotlin.math.sqrt

fun main() = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()
    val tests = readLine().toInt()

    for (tc in 1..tests) {
        val N = readLine().toLong()

        val factorList = getFactors(N)

        var answer = 0
        for (factor in factorList) {
            val str = factor.toString()
            if (isPalindrome(str)) {
                answer++
            }
        }

        sb.append("Case #${tc}: ${answer}\n")
    }

    print(sb)
}

fun isPalindrome(str: String): Boolean {
    val len = str.length
    val halfLen = str.length / 2

    for (i in 0 until halfLen) {
        if (str[i] != str[len - 1 - i]) {
            return false
        }
    }

    return true
}

fun getFactors(n: Long): List<Long> {
    val ret = mutableListOf<Long>()

    val sqrtN = sqrt(n.toDouble()).roundToLong()

    for (div in 1..sqrtN) {
        if (n % div == 0L) {
            ret.add(div)

            if (div < n / div) {
                ret.add(n / div)
            }
        }
    }

    return ret
}