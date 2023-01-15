const val INF = 98765432109876543L
const val INVALID = -1L
const val HELLO_BOJ = -9999

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    val tests = readLine()!!.trim().toInt()

    repeat(tests) {
        val n = readLine()!!.trim().toLong()
        val answer = solve(n)
        if (answer == HELLO_BOJ) {
            sb.append("Hello, BOJ 2023!")
        } else {
            sb.append(answer)
        }
        sb.append("\n")
    }

    print(sb)
}

fun solve(n: Long): Int {
    var ret = 0

    val digitList = getDigitList(n)
    if (isInfinite(digitList)) {
        return HELLO_BOJ
    } else {
        val set = getPossibleAdditions(digitList)
        for (m in 1 .. 30) {
            val sumOfPowers = getSumOfPowers(n, digitList, m)
            if (sumOfPowers > n || sumOfPowers < 0) {
                break
            }
            if (set.contains(sumOfPowers)) {
                ret++
            }
        }

        return ret
    }
}

/** 0과 1로만 이루어져 있으면 무한히 가능 */
fun isInfinite(digitList: List<Long>): Boolean {
    for (digit in digitList) {
        if (digit >= 2) {
            return false
        }
    }

    return true
}

/** n 의 digit 들을 리스트로 만들어서 리턴 */
fun getDigitList(n: Long): List<Long> {
    val list = mutableListOf<Long>()

    val str = n.toString()
    for (c in str) {
        list.add(c.minus('0').toLong())
    }

    return list

}

/** 자릿수마다 exponent 승 한 것의 합을 구해서 리턴 */
fun getSumOfPowers(n: Long, digitList: List<Long>, exponent: Int): Long {
    var ret = 0L

    for (digit in digitList) {
        ret += pow(digit, exponent)
        if (ret < 0 || ret > n) {
            return INVALID
        }
    }

    return ret
}

/** 제곱 계산 */
fun pow(base: Long, exponent: Int): Long {
    if (exponent == 0) {
        return 1L
    }

    val half = pow(base, exponent / 2)

    var ret = half * half
    if (ret < 0) {
        return INF
    }

    if (exponent % 2 != 0) {
        ret *= base
    }

    if (ret < 0) {
        return INF
    }

    return ret
}

/** 0개 이상의 + 로 만들 수 있는 모든 수를 리턴 */
fun getPossibleAdditions(digitList: List<Long>): HashSet<Long> {
    val ret = HashSet<Long>()

    val len = digitList.size
    for (bitmask in 0 until (1 shl (len - 1))) {
        ret.add(getSum(digitList, bitmask))
    }

    return ret
}

/** + 의 위치들이 bitmask 로 주어졌을 때 합을 리턴 */
fun getSum(digitList: List<Long>, bitmask: Int): Long {
    var sum = 0L
    var mult = 1L
    for (i in digitList.size - 1 downTo 0) {
        val digit = digitList[i]
        sum += (mult * digit)

        if ((bitmask and (1 shl (i - 1))) != 0) {
            mult *= 10
        } else {
            mult = 1
        }
    }

    return sum
}