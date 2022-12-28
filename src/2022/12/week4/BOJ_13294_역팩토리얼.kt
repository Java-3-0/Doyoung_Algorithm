import kotlin.math.log10

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val factorial = readLine()!!

    val answer = solve(factorial)

    println(answer)
}

fun solve(factorial: String): Int {
    return when (factorial) {
        "1" -> 1
        "2" -> 2
        "6" -> 3
        "24" -> 4
        "120" -> 5
        "620" -> 6
        "4340" -> 7
        "34720" -> 8
        "312480" -> 9
        else -> {
            val lenFactorial = factorial.length

            var logVal = 0.0
            var num = 1
            var ret = -1
            while (true) {
                logVal += log10(num.toDouble())

                if (logVal.toInt() > lenFactorial - 1) {
                    break
                } else if (logVal.toInt() == lenFactorial - 1) {
                    ret = num
                }

                num++
            }

            ret
        }
    }
}
