import java.util.*
import kotlin.math.min

const val MAX_V = 1000
const val INF = 987654321
const val NOT_ASSIGNED = -1

val edgeList = mutableListOf<Edge>()
val adjList = Array(MAX_V + 1) { mutableListOf<Int>() }
val capacities = Array(MAX_V + 1) { IntArray(MAX_V + 1) { 0 } }
val flows = Array(MAX_V + 1) { IntArray(MAX_V + 1) { 0 } }
val vertexLevels = IntArray(MAX_V + 1) { NOT_ASSIGNED }
val idxToStartSearch = IntArray(MAX_V + 1) { 0 }
var source = 0
var sink = 0

data class Edge(val from: Int, val to: Int, val cap: Int)

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    val tests = readLine()!!.trim().toInt()
    repeat(tests) {
        initMemory()

        val (V, E) = readLine()!!.trim().split(" ").map { it.toInt() }
        source = 1
        sink = V

        repeat(E) {
            val (from, to, cap) = readLine()!!.trim().split(" ").map { it.toInt() }

            edgeList.add(Edge(from, to, cap))
            capacities[from][to] += cap
            adjList[from].add(to)
            adjList[to].add(from)
        }

        // 최대 용량을 흘려 본다
        val maxFlow = getMaxFlow()

        var answer = 0
        for (edge in edgeList) {
            // 최대 용량이 흐르고 있는 간선 중, from 에서 to 로 잔여 용량이 남은 간선을 통해 갈 수 있는 경로가 없는 경우 완전 중요한 간선이다
            if (capacities[edge.from][edge.to] - flows[edge.from][edge.to] == 0) {
                if (!canFindPath(edge.from, edge.to)) {
                    answer++
                }
            }
        }
        sb.append("$answer\n")
    }
    print(sb)
}

fun canFindPath(from: Int, to: Int): Boolean {
    val isVisited = BooleanArray(MAX_V + 1) { false }
    val q = LinkedList<Int>()

    isVisited[from] = true
    q.offer(from)

    while (q.isNotEmpty()) {
        val cur = q.poll()
        if (cur == to) {
            return true
        }
        for (next in adjList[cur]) {
            if (!isVisited[next] && capacities[cur][next] - flows[cur][next] > 0) {
                isVisited[next] = true
                q.offer(next)
            }
        }
    }

    return false
}

fun initMemory() {
    edgeList.clear()
    for (list in adjList) {
        list.clear()
    }
    for (arr in capacities) {
        Arrays.fill(arr, 0)
    }
    for (arr in flows) {
        Arrays.fill(arr, 0)
    }
    Arrays.fill(vertexLevels, NOT_ASSIGNED)
    Arrays.fill(idxToStartSearch, 0)
}

/** 디닉 알고리즘을 통해 max flow 를 구해서 리턴한다 */
fun getMaxFlow(): Int {
    var ret = 0
    while (true) {
        setVertexLevelsByBFS()
        if (vertexLevels[sink] == NOT_ASSIGNED) {
            break
        }

        Arrays.fill(idxToStartSearch, 0)
        while (true) {
            val flow = findPathToSendFlowByDFS(source, INF)
            if (flow == 0) {
                break
            } else {
                ret += flow
            }
        }
    }

    return ret
}

/** 각 정점에 레벨을 매긴다. */
fun setVertexLevelsByBFS() {
    val q = LinkedList<Int>()
    Arrays.fill(vertexLevels, NOT_ASSIGNED)

    vertexLevels[source] = 0
    q.offer(source)

    while (q.isNotEmpty()) {
        val size = q.size
        repeat(size) {
            val cur = q.poll()

            // 아직 레벨이 정해지지 않고, 유량을 보낼 잔여 용량이 있는 곳인 경우 탐색
            for (next in adjList[cur]) {
                if (vertexLevels[next] == NOT_ASSIGNED && capacities[cur][next] - flows[cur][next] > 0) {
                    vertexLevels[next] = vertexLevels[cur] + 1
                    q.offer(next)
                }
            }
        }
    }
}

/** flow 를 보낼 경로를 dfs 로 찾고, 그 flow 양을 리턴한다 */
fun findPathToSendFlowByDFS(cur: Int, bottleneck: Int): Int {
    if (cur == sink) {
        return bottleneck
    }

    val front = idxToStartSearch[cur]
    for (i in front until adjList[cur].size) {
        val next = adjList[cur][i]
        val remainingCapacity = capacities[cur][next] - flows[cur][next]
        if (vertexLevels[next] == vertexLevels[cur] + 1 && remainingCapacity > 0) {
            val flowToSend = findPathToSendFlowByDFS(next, min(bottleneck, remainingCapacity))
            if (flowToSend > 0) {
                flows[cur][next] += flowToSend
                flows[next][cur] -= flowToSend
                return flowToSend
            }
        }

        idxToStartSearch[cur]++
    }

    return 0
}