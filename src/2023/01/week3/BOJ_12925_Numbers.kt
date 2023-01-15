const val MOD = 1000L

class Matrix(val arr: Array<LongArray>) {
    fun canMultiply(other: Matrix): Boolean {
        return (arr[0].size == other.arr.size)
    }

    fun multiply(other: Matrix): Matrix {
        val ret = Matrix(Array(arr.size) { LongArray(other.arr.size) { 0L } })
        for (y in arr.indices) {
            for (x in other.arr.indices) {
                var sum = 0L
                for (k in arr[y].indices) {
                    sum += arr[y][k] * other.arr[k][x]
                    sum %= MOD
                }
                ret.arr[y][x] = sum
            }
        }

        return ret
    }

    fun power(exponent: Long): Matrix {
        if (exponent == 1L) {
            return this
        }

        val half = this.power(exponent / 2)
        return if (exponent % 2L == 0L) {
            half.multiply(half)
        } else {
            half.multiply(half).multiply(this)
        }
    }

    fun print() {
        val sbTmp = StringBuilder()
        for (y in arr.indices) {
            for (x in arr[y].indices) {
                sbTmp.append("${arr[y][x]}\t")
            }
            sbTmp.append("\n")
        }
        sbTmp.append("\n")
        print(sbTmp)
    }
}

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    val matrix = Matrix(arrayOf(longArrayOf(6L, -4L), longArrayOf(1L, 0L)))

    val tests = readLine()!!.trim().toInt()
    for (tc in 1 .. tests) {
        val n = readLine()!!.trim().toLong()
        val powered = matrix.power(n - 1L)
        val c = powered.arr[1][0]
        val d = powered.arr[1][1]
        val number = 28L * c + 6L * d
        val answer = ((number - 1) + 1000000000000L) % MOD
        sb.append("Case #${tc}: ${String.format("%03d", answer)}\n")
    }

    print(sb)
}