import java.util.*
import kotlin.math.ln

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val N = readLine()!!.toInt()
    val M = readLine()!!.toInt()

    val usedPasswordList = readLine()!!.split(" ").map { it.toInt() }

    val q = LinkedList<Int>()
    val isVisited = BooleanArray(N + 1) { false }
    for (usedPassword in usedPasswordList) {
        isVisited[usedPassword] = true
        q.offer(usedPassword)
    }

    val bitSize = 1 + (ln(N.toDouble()) / ln(2.0)).toInt()
    var depth = 0
    var cnt = 0
    while (!q.isEmpty()) {
        val size = q.size
        repeat(size) {
            val cur = q.poll()
            cnt++
            for (bitIdx in 0 until bitSize) {
                val next = (cur xor (1 shl bitIdx))
                if (next in 0 .. N && !isVisited[next]) {
                    isVisited[next] = true
                    q.offer(next)
                }
            }
        }

        depth++
    }

    println(depth - 1)
}