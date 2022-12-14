fun main() = with(System.`in`.bufferedReader()) {
    val angle = (readLine().toDouble() * 100.0).toInt();
    val pizza = 36000;

    val answer = pizza / getGCD(angle, pizza)
    println(answer)
}

fun getGCD(a: Int, b: Int): Int {
    return if (a == 0) b else getGCD(b % a, a)
}