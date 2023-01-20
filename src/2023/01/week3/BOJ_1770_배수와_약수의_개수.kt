import Mod64.modAdd
import Mod64.modMult
import Mod64.modPow
import java.math.BigInteger
import java.util.*

val factorials = mutableListOf<BigInteger>()

fun precalcFactorials(limit: Int) {
    factorials.add(BigInteger.ONE)

    for (i in 1 .. limit) {
        factorials.add(factorials[i - 1] * i.toBigInteger())
    }
}

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    precalcFactorials(25)

    val tests = readLine()!!.trim().toInt()
    repeat(tests) {
        // n을 소인수분해 하고 각 (소인수 -> 차수)를 매핑한다
        val n = readLine()!!.trim().toULong()
        val primeFactorList = PollardRho.getPrimeFactors(n)
        val factorCntMap = HashMap<ULong, Int>()
        primeFactorList.forEach { item -> factorCntMap[item] = 1 + factorCntMap.getOrDefault(item, 0) }

        val exponentList = factorCntMap.values.toList()

        val answer =
            // n == 4 인 경우 예외처리
            if (n == 4UL) {
                1
            }
            // 그 외의 경우
            else {
                if (exponentList.isNotEmpty() && exponentList.max() > 1) {
                    -1
                } else {
                    factorials[exponentList.size]
                }
            }

        sb.append("$answer\n")
    }

    print(sb)

}

object PollardRho {
    private val random = Random()

    /** 폴라드-로 알고리즘을 이용하여 n 을 소인수분해한 결과를 리스트로 리턴 */
    fun getPrimeFactors(n: ULong): List<ULong> {
        val ret = mutableListOf<ULong>()

        /** n 을 재귀적으로 소인수분해하는 함수 */
        fun factorizeRec(n: ULong) {
            /** u(i) 가 u(i+1) = u(i) * u(i) + c 의 관계를 가질 때 다음 항을 구해서 리턴하는 함수 */
            fun getNextTerm(x: ULong, c: ULong): ULong {
                return modAdd(modMult(x, x, n), c, n)
            }

            // 1인 경우
            if (n == 1UL) {
                return
            }

            // 짝수인 경우
            if (n % 2UL == 0UL) {
                ret.add(2UL)
                factorizeRec(n / 2UL)
                return
            }

            if (MillerRabin64.isPrime(n)) {
                ret.add(n)
                return
            }

            // 초기항 u(0) 은 [2, n) 사이 값을 하나 뽑는다
            var x = random.nextLong().toULong() % (n - 2UL) + 2UL
            var y = x
            // 상수 c는 [1, n) 사이 값을 하나 뽑는다
            var c = random.nextLong().toULong() % (n - 2UL) + 1UL

            // 수열의 주기성을 이용해서 소인수 하나를 찾는다
            while (true) {
                // x = u(s), y = u(2s) 를 구한다
                x = getNextTerm(x, c)
                y = getNextTerm(getNextTerm(y, c), c)

                // u(2s) - u(s) 를 확인한다
                val diff = if (x <= y) y - x else x - y
                val gcd = getGCD(diff, n)

                // n 은 gcd 로 나누어떨어진다는 것을 찾은 경우 gcd 와 n / gcd 를 계속해서 소인수분해
                if (gcd != 1UL) {
                    factorizeRec(gcd)
                    factorizeRec(n / gcd)
                    break
                }
            }
        }

        // 소인수분해하여 ret 에 담아서 리턴
        factorizeRec(n)
        return ret.sorted()
    }

    /** a, b의 최대공약수를 구해서 리턴 */
    private fun getGCD(a: ULong, b: ULong): ULong {
        return if (a == 0UL) b else getGCD(b % a, a)
    }
}

object MillerRabin64 {
    /** 밀러 라빈 소수 판정법으로 구한 n의 소수 여부를 리턴 */
    fun isPrime(n: ULong): Boolean {
        val aList = listOf(2UL, 325UL, 9375UL, 28178UL, 450775UL, 9780504UL, 1795265022UL)

        // 1, 2 처리
        if (n <= 1UL) {
            return false
        }
        if (n == 2UL) {
            return true
        }

        // 짝수 처리
        if (n % 2UL == 0UL) {
            return false
        }

        // n 이 작으면 그냥 일반적인 sqrt(n) 소수 판정법으로 판정
        if (n <= 10000UL) {
            var div = 3UL
            while (div * div <= n) {
                if (n % div == 0UL) {
                    return false
                }
                div += 2UL
            }
            return true
        }

        // 인수들 중 n의 배수가 존재하는지 여부를 탐색
        for (a in aList) {
            if (a >= n) {
                break
            }

            // 소수일 가능성이 없으면 false 를 리턴
            if (!isProbablePrime(n, a)) return false
        }

        // listA 에 있는 모든 a 에 대해 소수일 가능성이 있다고 판정되면 소수인 것으로 판정
        return true
    }

    /** (n, a)로 밀러-라빈 테스트를 했을 때 소수일 가능성이 있으면 true, 확실히 합성수이면 false 를 리턴 */
    private fun isProbablePrime(n: ULong, a: ULong): Boolean {
        var d = n - 1UL

        // 높은 차수 항부터 (a^d + 1) % n == 0 인지 하나씩 확인
        while (d > 0UL) {
            d /= 2UL

            if (modPow(a, d, n) == n - 1UL) {
                return true
            }

            if (d % 2UL != 0UL) {
                break
            }
        }

        // 마지막 차수에 대해서 (a^d - 1) % n == 1 인지 확인
        if (modPow(a, d, n) == 1UL) {
            return true
        }

        return false
    }
}

object Mod64 {
    /** 오버플로우가 나지 않게 처리한 모듈러 덧셈 */
    fun modAdd(a: ULong, b: ULong, mod: ULong): ULong {
        val x = a % mod
        val y = b % mod
        return if (x >= mod - y) (x - (mod - y)) else (x + y)
    }

    /** 모듈러 곱셈 */
    fun modMult(a: ULong, b: ULong, mod: ULong): ULong {
        val x = a % mod
        val y = b % mod

        if (x < y) {
            return modMult(y, x, mod)
        }

        if (y == 0UL) {
            return 0UL
        }

        val half = modMult(x, y / 2UL, mod)
        return if (y % 2UL == 0UL) {
            modAdd(half, half, mod)
        } else {
            modAdd(modAdd(half, half, mod), x, mod)
        }
    }

    /** 모듈러 제곱 */
    fun modPow(base: ULong, exponent: ULong, mod: ULong): ULong {
        val x = base % mod

        if (exponent == 0UL) {
            return 1UL
        }

        val half = modPow(base, exponent / 2UL, mod)
        return if (exponent % 2UL == 0UL) {
            modMult(half, half, mod)
        } else {
            modMult(modMult(half, half, mod), x, mod)
        }
    }
}