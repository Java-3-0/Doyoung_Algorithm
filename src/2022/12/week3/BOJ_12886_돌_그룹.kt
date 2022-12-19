import java.util.*


fun main() = with(System.`in`.bufferedReader()) {
    val (A, B, C) = readLine().split(" ").map { it.toInt() }

    val answer = bfs(A, B, C)

    println(answer)

}

fun bfs(a: Int, b: Int, c: Int): Int {
    val isVisited = Array(500 + 1) { BooleanArray(750 + 1) { false } }
    val queue = LinkedList<List<Int>>()

    isVisited[a][b] = true
    queue.offer(listOf(a, b, c).sorted())

    while (!queue.isEmpty()) {
        val now = queue.poll()!!
        if (now[0] == now[1] && now[1] == now[2]) {
            return 1
        }

        for (i in 0 .. 1) {
            for (j in (i + 1) .. 2) {
                val x = now[i]
                val y = now[j]
                val z = now[3 - i - j]

                val nextX = x + x
                val nextY = y - x

                val next = listOf(nextX, nextY, z).sorted()
                if (!isVisited[next[0]][next[1]]) {
                    isVisited[next[0]][next[1]] = true
                    queue.offer(next)
                }
            }
        }
    }

    return 0
}