import kotlin.math.ceil
import kotlin.math.sqrt

fun main() = with(System.`in`.bufferedReader()) {
    val (a2, a1, a0) = readLine().split(" ").map { it.toInt() }
    val c = readLine().toInt()
    val n0 = readLine().toInt()

    val answer = if (isGreaterThanOrEqualToZero(c - a2, -a1, -a0, n0)) 1 else 0

    println(answer)
}

// ax^2 + bx + c >= 0이 x0 이상에서 항상 성립하는지 여부를 리턴
fun isGreaterThanOrEqualToZero(a: Int, b: Int, c: Int, x0: Int): Boolean {
    // f(x0)가 음수인 경우 false
    val f0 = a * x0 * x0 + b * x0 + c
    if (f0 < 0) {
        return false
    }

    // 2차항의 계수가 음수인 경우
    if (a < 0) {
        return false
    }

    // 2차항의 계수가 양수인 경우
    else if (a > 0) {
        // 허수 해나 중근을 갖는 경우
        if (b * b - 4 * a * c <= 0) {
            return true
        }
        // x0가 오른쪽 근이거나 그보다 큰 경우
        else {
            val right = (-b.toDouble() + sqrt((b * b - 4 * a * c).toDouble())) / (2 * a).toDouble()
            return x0 >= ceil(right - 0.00000000001).toInt()
        }
    }

    // 2차항의 계수가 0인 경우
    else {
        return b >= 0
    }
}
