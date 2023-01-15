const val MOD = 1000000007L

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