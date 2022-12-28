import java.util.*
import kotlin.math.min

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val (N, K) = readLine()!!.split(" ").map { it.toInt() }
    val text = readLine()!!

    var cntT = 0
    text.forEach { c -> if (c == 'T') cntT++ }

    val answer = bfs(N, K, cntT)

    println(answer)
}

fun bfs(N: Int, K: Int, start: Int): Int {
    val q = LinkedList<Int>()
    val isVisited = BooleanArray(N + 1) { false }

    isVisited[start] = true
    q.offer(start)

    var depth = 0
    while (q.isNotEmpty()) {
        val size = q.size
        repeat(size) {
            val curTails = q.poll()!!
            if (curTails == N) {
                return depth
            }

            val curHeads = N - curTails

            for (tailPicked in 0 .. min(curTails, K)) {
                val headPicked = K - tailPicked
                if (headPicked in 0 .. curHeads) {
                    val next = curTails - tailPicked + headPicked
                    if (!isVisited[next]) {
                        isVisited[next] = true
                        q.offer(next)
                    }
                }
            }

        }

        depth++
    }

    return -1
}
