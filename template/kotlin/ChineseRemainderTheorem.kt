object ChineseRemainderTheorem {
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