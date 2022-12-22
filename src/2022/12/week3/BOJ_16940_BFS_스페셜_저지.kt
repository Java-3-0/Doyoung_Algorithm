import java.util.*

const val MAX_V = 100000
const val NOT_VISITED = -1

var V = 0
var adjList = Array(MAX_V + 1) { mutableListOf<Int>() }
var targetVisitOrder = IntArray(MAX_V + 1) { NOT_VISITED }
var visitOrder = IntArray(MAX_V + 1) { NOT_VISITED }

fun main() = with(System.`in`.bufferedReader()) {
    V = readLine()!!.toInt()

    repeat(V - 1) {
        val (from, to) = readLine()!!.split(" ").map { it.toInt() }
        adjList[from].add(to)
        adjList[to].add(from)
    }

    val list = readLine()!!.split(" ").map { it.toInt() }
    list.forEachIndexed { index, num -> targetVisitOrder[num] = index + 1 }

    for (i in adjList.indices) {
        adjList[i].sortWith { v1, v2 -> targetVisitOrder[v1].compareTo(targetVisitOrder[v2]) }
    }

    bfs(1)

    println(if (isCorrectOrder()) 1 else 0)
}

fun bfs(start: Int) {
    val q = LinkedList<Int>()

    var cnt = 0

    visitOrder[start] = (++cnt)
    q.offer(start)

    while (q.isNotEmpty()) {
        val now = q.poll()
        for (next in adjList[now]) {
            if (visitOrder[next] == NOT_VISITED) {
                visitOrder[next] = (++cnt)
                q.offer(next)
            }
        }
    }
}

fun isCorrectOrder(): Boolean {
    for (v in 1 .. V) {
        if (visitOrder[v] != targetVisitOrder[v]) {
            return false
        }
    }
    return true
}