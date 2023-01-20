import Mod.modAdd
import Mod.modMult

object Lucas {
    private const val CACHE_EMPTY = -1L

    fun getModCombination(n: Long, k: Long, mod: Long): Long {
        val cache = Array(mod.toInt() + 1) { LongArray(mod.toInt() + 1) { CACHE_EMPTY } }

        /** mod 이하의 수 n, k에 대해서 (nCk % mod)를 구해서 리턴 */
        fun getSmallCombination(n: Int, k: Int): Long {
            if (n < k) {
                return 0L
            }

            if (k == 0 || k == n) {
                return 1L
            }

            if (cache[n][k] != CACHE_EMPTY) {
                return cache[n][k]
            }

            var ret = modAdd(getSmallCombination(n - 1, k - 1), getSmallCombination(n - 1, k), mod)
            cache[n][k] = ret
            return ret
        }

        var ret = 1L
        var curN = n
        var curK = k
        while (curN > 0 && curK > 0) {
            val digitN = curN % mod
            val digitK = curK % mod

            val nCk = getSmallCombination(digitN.toInt(), digitK.toInt())
            ret = modMult(ret, nCk, mod)

            curN /= mod
            curK /= mod
        }

        return ret
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