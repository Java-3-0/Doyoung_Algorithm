import Mod32.modPow

object MillerRabin32 {
    /** 밀러 라빈 소수 판정법으로 구한 n의 소수 여부를 리턴 */
    fun isPrime(n: ULong): Boolean {
        val aList = listOf(2UL, 7UL, 61UL)

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

object Mod32 {
    /** 오버플로우가 나지 않게 처리한 모듈러 덧셈 */
    fun modAdd(a: ULong, b: ULong, mod: ULong): ULong {
        return (a + b) % mod
    }

    /** 모듈러 곱셈 */
    fun modMult(a: ULong, b: ULong, mod: ULong): ULong {
        return (a * b) % mod
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