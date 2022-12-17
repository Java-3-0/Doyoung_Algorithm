import java.util.*

val dy = arrayOf(-1, 1, 0, 0)
val dx = arrayOf(0, 0, -1, 1)

data class Vertex(val y: Int, val x: Int, val distance: Int) : Comparable<Vertex> {
    override fun compareTo(other: Vertex): Int {
        return compareValues(this.distance, other.distance)
    }
}

fun main() = with(System.`in`.bufferedReader()) {
    val (W, H) = readLine().split(" ").map { it.toInt() }

    val grid = Array(H) { IntArray(W) }
    for (y in 0 until H) {
        val line = readLine()
        for (x in 0 until W) {
            grid[y][x] = line[x].minus('0')
        }
    }

    val answer = dijkstra(H, W, grid, 0, 0, H - 1, W - 1)
    println(answer)
}

fun dijkstra(H: Int, W: Int, grid: Array<IntArray>, startY: Int, startX: Int, endY: Int, endX: Int): Int {
    val isVisited = Array(H) { BooleanArray(W) }
    val pq = PriorityQueue<Vertex>()

    // 시작 정점 처리
    pq.offer(Vertex(startY, startX, 0))

    // 다익스트라 알고리즘 수행
    while (!pq.isEmpty()) {
        val now = pq.poll()

        if (isVisited[now.y][now.x]) {
            continue
        }

        isVisited[now.y][now.x] = true
        if (now.y == endY && now.x == endX) {
            return now.distance
        }

        for (dir in 0 until 4) {
            val nextY = now.y + dy[dir]
            val nextX = now.x + dx[dir]
            if (nextY in 0 until H && nextX in 0 until W && !isVisited[nextY][nextX]) {
                pq.offer(Vertex(nextY, nextX, now.distance + grid[nextY][nextX]))
            }
        }
    }

    return -1
}