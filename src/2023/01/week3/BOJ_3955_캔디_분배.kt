fun main(): Unit = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    val tests = readLine()!!.trim().toInt()

    repeat(tests) {
        val (k, c) = readLine()!!.trim().split(" ").map { it.toLong() }

        // c * x = 1 (mod k) 의 해 x 를 구한다
        var answer = ExtendEuclidean.getMultInv(c, k)

        if (answer == -1L) {
            sb.append("IMPOSSIBLE")
        } else {
            while (c * answer < k + 1) {
                answer += k
            }

            if (answer <= 1_000_000_000L) {
                sb.append(answer)
            } else {
                sb.append("IMPOSSIBLE")
            }
        }
        sb.append("\n")
    }

    print(sb)
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


