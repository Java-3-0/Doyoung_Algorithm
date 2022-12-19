import kotlin.math.sqrt

fun main() = with(System.`in`.bufferedReader()) {
    val (N, M) = readLine().split(" ").map { it.toInt() }
    val listD = readLine().split(" ").map { it.toLong() }
    val listM = readLine().split(" ").map { it.toLong() }

    var commonLCM = listD[0]
    for (i in 1 until N) {
        commonLCM = getLCM(commonLCM, listD[i])
    }

    var commonGCD = listM[0]
    for (i in 1 until M) {
        commonGCD = getGCD(commonGCD, listM[i])
    }

    if (commonGCD % commonLCM != 0L) {
        println(0)
    } else {
        val num = commonGCD / commonLCM
        // num의 약수 개수를 센다
        var answer = 0
        val sqrt = sqrt(num.toDouble()).toLong()
        for (div in 1..sqrt) {
            if (num % div == 0L) {
                answer += 2
            }
        }
        if (sqrt * sqrt == num) {
            answer--;
        }

        println(answer)
    }
}

fun getGCD(a: Long, b: Long): Long {
    return if (a == 0L) b else getGCD(b % a, a)
}

fun getLCM(a: Long, b: Long): Long {
    return a / getGCD(a, b) * b
}