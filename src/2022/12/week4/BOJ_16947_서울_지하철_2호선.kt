import java.util.*
import kotlin.math.min

const val MAX_V = 3000

var V = 0
val adjList = Array(MAX_V + 1) { mutableListOf<Int>() }
val discovered = IntArray(MAX_V + 1) { -1 }
var isAssigned = BooleanArray(MAX_V + 1) { false }
val isInCycle = BooleanArray(MAX_V + 1) { false }
val stack = Stack<Int>()
var visitCnt = 0

fun main() = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    // 정점 수 입력
    V = readLine()!!.toInt()

    // 간선 입력
    repeat(V) {
        val (a, b) = readLine().split(" ").map { it.toInt() }
        adjList[a].add(b)
        adjList[b].add(a)
    }

    // 순환선 찾기
    for (v in 1 .. V) {
        if (discovered[v] === -1) {
            dfs(v, -1)
        }
    }

    // multi source bfs 로 각 정점의 순환선으로부터의 거리 계산
    val distances = getDistancesByMultiSourceBFS()

    // 출력
    for (v in 1 .. V) {
        sb.append("${distances[v]} ")
    }
    sb.append("\n")

    print(sb)

}

fun dfs(cur: Int, prev: Int): Int {
    discovered[cur] = ++visitCnt
    var ret = discovered[cur]

    stack.push(cur)
    for (next in adjList[cur]) {
        if (next == prev) {
            continue
        }

        // 트리 간선인 경우
        if (discovered[next] === -1) {
            ret = min(ret, dfs(next, cur))
        }

        // 아직 scc로 묶이지 않은 정점으로 가는 교차 간선인 경우
        else if (!isAssigned[next]) {
            ret = min(ret, discovered[next])
        }

    }

    if (ret == discovered[cur]) {
        val scc = mutableListOf<Int>()
        while (!stack.isEmpty()) {
            val top = stack.pop()
            scc.add(top)
            isAssigned[top] = true
            if (top == cur) {
                break
            }
        }

        if (scc.size >= 2) {
            for (v in scc) {
                isInCycle[v] = true
            }
        }
    }

    return ret
}

fun getDistancesByMultiSourceBFS(): IntArray {
    val distances = IntArray(V + 1) { 0 }
    val q = LinkedList<Int>()
    val isVisited = BooleanArray(V + 1) { false }

    // q 에 순환선에 있는 모든 정점 넣기
    for (v in 1 .. V) {
        if (isInCycle[v]) {
            isVisited[v] = true
            q.offer(v)
        }
    }

    // bfs 수행
    var depth = 0
    while (q.isNotEmpty()) {
        val size = q.size
        repeat(size) {
            val here = q.poll()
            distances[here] = depth

            for (next in adjList[here]) {
                if (!isVisited[next]) {
                    isVisited[next] = true
                    q.offer(next)
                }
            }
        }
        depth++
    }

    return distances
}