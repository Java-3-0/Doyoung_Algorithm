import java.util.*

const val MAX_V = 100000
const val MAX_E = 1000000

var V = 0
var E = 0

val adjList = Array(MAX_V + 1) { mutableListOf<Int>() }
val isVisited = BooleanArray(MAX_V + 1) { false }

fun main() = with(System.`in`.bufferedReader()) {
    // 정점 수, 간선 수 입력
    val st = StringTokenizer(readLine(), " ")
    V = st.nextToken().toInt()
    E = st.nextToken().toInt()

    // 간선 정보 입력
    repeat(E) {
        val (v1, v2) = readLine().split(" ").map { it.toInt() }
        adjList[v1].add(v2)
        adjList[v2].add(v1)
    }

    // 전체 경우의 수 계산
    var allCnt = V.toLong() * (V - 1).toLong()

    // 성공하는 경우의 수 계산
    var possibleCnt = 0L
    for (start in 0 until V) {
        if (!isVisited[start]) {
            val visitCnt = bfs(start)
            possibleCnt += (visitCnt.toLong() * (visitCnt - 1).toLong())
        }
    }

    // 정답 계산
    val answer = possibleCnt.toDouble() / allCnt.toDouble()

    // 출력
    println(String.format("%.10f", answer))
}

// start 로부터 bfs 를 돌고 방문한 정점 수를 리턴
fun bfs(start: Int): Int {
    var ret = 0
    val queue: Queue<Int> = LinkedList()

    isVisited[start] = true
    queue.offer(start)
    ret++

    while (!queue.isEmpty()) {
        val now = queue.poll()

        for (next in adjList[now]) {
            if (!isVisited[next]) {
                isVisited[next] = true
                queue.offer(next)
                ret++
            }
        }
    }

    return ret
}