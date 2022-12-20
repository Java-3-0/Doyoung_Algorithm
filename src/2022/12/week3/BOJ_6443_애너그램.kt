import java.util.*

const val ALPHABETS = 26

var len = 0
val counts = IntArray(ALPHABETS) { 0 }
val sb = StringBuilder()
val sbTmp = StringBuilder()

fun main() = with(System.`in`.bufferedReader()) {
    val tests = readLine()!!.toInt()

    repeat(tests) {
        val text = readLine()!!
        len = text.length

        Arrays.fill(counts, 0)
        text.forEach { c -> counts[c.minus('a')]++ }

        solve(0)
    }

    print(sb)

}

fun solve(cnt: Int) {
    if (cnt == len) {
        sb.append(sbTmp.toString()).append("\n")
        return
    }

    for (i in 0 until ALPHABETS) {
        if (counts[i] > 0) {
            val c = 'a'.plus(i)
            counts[i]--
            sbTmp.append(c)
            solve(cnt + 1)
            sbTmp.setLength(sbTmp.length - 1)
            counts[i]++
        }


    }
}
