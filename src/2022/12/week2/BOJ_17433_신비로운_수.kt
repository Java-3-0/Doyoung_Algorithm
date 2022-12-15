fun main() = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()
    val tests = readLine().toInt()

    repeat(tests) {
        val N = readLine().toInt()
        val list = readLine().split(" ").map { it.toInt() }.sorted()

        val diffList = mutableListOf<Int>()
        for (i in 1 until N) {
            diffList.add(list[i] - list[i - 1])
        }

        var gcd = diffList[0]
        for (i in 1 until diffList.size) {
            gcd = getGCD(gcd, diffList[i])
        }

        val answer = if (gcd == 0) "INFINITY" else "$gcd"
        sb.append("$answer\n")
    }

    print(sb)
}

fun getGCD(a: Int, b: Int): Int {
    return if (a == 0) b else getGCD(b % a, a)
}

