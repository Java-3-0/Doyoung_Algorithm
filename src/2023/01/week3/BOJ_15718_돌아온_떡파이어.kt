import Mod.modMult
import java.util.*

const val CACHE_EMPTY = -1L
const val MAX_MOD = 1031

val factorials97 = mutableListOf<Long>()
val factorials1031 = mutableListOf<Long>()

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    precalcFactorials(1031)

    val tests = readLine()!!.trim().toInt()

    repeat(tests) {
        val (a, b) = readLine().trim().split(" ").map { it.toLong() }

        val n = a - 1L
        val k = b - 2L

        // 떡을 하나도 먹지 않고 바로 죽는 경우를 예외처리
        if (a == 0L && b == 1L) {
            sb.append(1)
        }
        // 불가능한 경우
        else if (k < 0 || n < 0 || n < k) {
            sb.append(0)
        }
        // 가능한 경우
        else {
            // 100007 = 97 * 1031
            val r1 = Lucas.getModCombination(n, k, 97L)
            val r2 = Lucas.getModCombination(n, k, 1031L)

            // 연립 합동식을 CRT 로 푼다
            val equationList = mutableListOf<CRT.Equation>()
            equationList.add(CRT.Equation(r1, 97L))
            equationList.add(CRT.Equation(r2, 1031L))

            // 97과 1031이 서로소니까 답은 항상 가능
            val answer = CRT.solve(equationList)
            sb.append("${answer.r}")
        }

        sb.append("\n")

    }

    print(sb)
}

fun precalcFactorials(limit: Int) {
    factorials97.add(1L)
    factorials1031.add(1L)

    for (i in 1 .. limit) {
        factorials97.add((factorials97[i - 1] * i) % 97)
        factorials1031.add((factorials1031[i - 1] * i) % 1031)
    }
}

object Lucas {
    private val cache = Array(MAX_MOD + 1) { LongArray(MAX_MOD + 1) { CACHE_EMPTY } }

    fun getModCombination(n: Long, k: Long, mod: Long): Long {
        for (i in cache.indices) {
            Arrays.fill(cache[i], CACHE_EMPTY)
        }

        var ret = 1L
        var curN = n
        var curK = k
        while (curN > 0 && curK > 0) {
            val digitN = curN % mod
            val digitK = curK % mod

            val nCk = getSmallCombination(digitN.toInt(), digitK.toInt(), mod)
            ret = modMult(ret, nCk, mod)

            curN /= mod
            curK /= mod
        }

        return ret
    }

    /** mod 이하의 수 n, k에 대해서 (nCk % mod)를 구해서 리턴 */
    private fun getSmallCombination(n: Int, k: Int, mod: Long): Long {
        if (n < k) {
            return 0L
        }

        when (mod) {
            97L -> {
                val a = factorials97[n]
                val b = ExtendEuclidean.getMultInv(factorials97[k], 97L)
                val c = ExtendEuclidean.getMultInv(factorials97[n - k], 97L)

                return modMult(modMult(a, b, 97L), c, 97L)
            }

            1031L -> {
                val a = factorials1031[n]
                val b = ExtendEuclidean.getMultInv(factorials1031[k], 1031L)
                val c = ExtendEuclidean.getMultInv(factorials1031[n - k], 1031L)

                return modMult(modMult(a, b, 1031L), c, 1031L)
            }

            else -> {
                return -1
            }
        }
    }
}

object CRT {
    /** x = a (mod m) 이라는 합동식 하나 */
    data class Equation(val a: Long, val m: Long)

    /** 중국인의 나머지 정리 결과 */
    data class ChineseRemainderResult(val r: Long, val m: Long, val isPossible: Boolean)

    /** 여러 합동식을 중국인의 나머지 정리로 푼다 */
    fun solve(equationList: List<Equation>): ChineseRemainderResult {
        var a = equationList[0].a
        var m = equationList[0].m
        a %= m

        for (i in 1 until equationList.size) {
            val equation = equationList[i]
            var a2 = equation.a
            var m2 = equation.m

            val gcd = ExtendEuclidean.getGCD(m, m2)

            if (a % gcd != a2 % gcd) {
                return ChineseRemainderResult(0L, 0L, false)
            }

            val euclideanResult = ExtendEuclidean.solveExtendedEuclidean(m / gcd, m2 / gcd)

            val s = euclideanResult.s
            val t = euclideanResult.t

            val mod = m / gcd * m2
            a = modMult(modMult(a, m2 / gcd, mod), t, mod) + modMult(modMult(a2, m / gcd, mod), s, mod)

            a = (a + mod) % mod
            m = mod
        }

        return ChineseRemainderResult(a, m, true)
    }
}

object ExtendEuclidean {
    data class EuclideanResult(val gcd: Long, val s: Long, val t: Long)

    /** a * s + b * t = gcd(a, b)가 되는 gcd, s, t 값을 구해서 리턴 */
    fun solveExtendedEuclidean(a: Long, b: Long): EuclideanResult {
        var sPrev = 1L
        var sNow = 0L
        var tPrev = 0L
        var tNow = 1L
        var rPrev = a
        var rNow = b

        while (rNow != 0L) {
            val q = rPrev / rNow

            val rTmp = rPrev % rNow
            rPrev = rNow
            rNow = rTmp

            val sTmp = sPrev - sNow * q
            sPrev = sNow
            sNow = sTmp

            val tTmp = tPrev - tNow * q
            tPrev = tNow
            tNow = tTmp
        }

        return EuclideanResult(rPrev, sPrev, tPrev)
    }

    fun getMultInv(a: Long, mod: Long): Long {
        val gcd = getGCD(a, mod)
        return if (gcd != 1L) -1L else (solveExtendedEuclidean(a, mod).s + mod) % mod
    }

    fun getGCD(a: Long, b: Long): Long {
        return if (a == 0L) b else getGCD(b % a, a)
    }
}

object Mod {
    /** 모듈러 덧셈 */
    fun modAdd(a: Long, b: Long, mod: Long): Long {
        return (a + b) % mod
    }

    /** 모듈러 곱셈 */
    fun modMult(a: Long, b: Long, mod: Long): Long {
        return (a * b) % mod
    }
}