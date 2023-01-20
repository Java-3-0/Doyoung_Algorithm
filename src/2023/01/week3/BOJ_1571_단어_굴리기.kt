import java.math.BigInteger

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val n = readLine()!!.trim().toInt()

    val textList = mutableListOf<String>()
    repeat(n) {
        textList.add(readLine()!!)
    }

    val target = readLine()!!

    val equationList = mutableListOf<ChineseRemainderTheorem.Equation>()
    var flag = true
    for (i in 0 until n) {
        val text = textList[i]
        val c = target[i]

        val idx = text.indexOf(c)
        val len = text.length

        if (idx != -1) {
            equationList.add(ChineseRemainderTheorem.Equation(idx.toBigInteger(), len.toBigInteger()))
        }
        else {
            flag = false
            break
        }
    }

    if (flag) {
        val result = ChineseRemainderTheorem.solve(equationList)
        val answer = if (result.isPossible) result.r else -BigInteger.ONE
        println(answer)
    } else {
        println(-1)
    }
}

object ChineseRemainderTheorem {
    /** x = a (mod m) 이라는 합동식 하나 */
    data class Equation(val a: BigInteger, val m: BigInteger)

    /** 중국인의 나머지 정리 결과 */
    data class ChineseRemainderResult(val r: BigInteger, val m: BigInteger, val isPossible: Boolean)

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
                return ChineseRemainderResult(BigInteger.ZERO, BigInteger.ZERO, false)
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
    data class EuclideanResult(val gcd: BigInteger, val s: BigInteger, val t: BigInteger)

    /** a * s + b * t = gcd(a, b)가 되는 gcd, s, t 값을 구해서 리턴 */
    fun solveExtendedEuclidean(a: BigInteger, b: BigInteger): EuclideanResult {
        var sPrev = BigInteger.ONE
        var sNow = BigInteger.ZERO
        var tPrev = BigInteger.ZERO
        var tNow = BigInteger.ONE
        var rPrev = a
        var rNow = b

        while (rNow != BigInteger.ZERO) {
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

    fun getMultInv(a: BigInteger, mod: BigInteger): BigInteger {
        val gcd = getGCD(a, mod)
        return if (gcd != BigInteger.ONE) -BigInteger.ONE else (solveExtendedEuclidean(a, mod).s + mod) % mod
    }

    fun getGCD(a: BigInteger, b: BigInteger): BigInteger {
        return if (a == BigInteger.ZERO) b else getGCD(b % a, a)
    }
}

fun modAdd(a: BigInteger, b: BigInteger, mod: BigInteger): BigInteger {
    return (a + b) % mod
}

fun modMult(a: BigInteger, b: BigInteger, mod: BigInteger): BigInteger {
    return (a * b) % mod
}