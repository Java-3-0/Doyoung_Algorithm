import java.util.*
import kotlin.math.min

const val MAX_V = 1000
const val INF = 98765432109876543L
const val NOT_ASSIGNED = -1

val adjList = Array(MAX_V + 1) { mutableListOf<Int>() }
val capacities = Array(MAX_V + 1) { LongArray(MAX_V + 1) { 0L } }
val flows = Array(MAX_V + 1) { LongArray(MAX_V + 1) { 0L } }
val vertexLevels = IntArray(MAX_V + 1) { NOT_ASSIGNED }
val idxToStartSearch = IntArray(MAX_V + 1) { 0 }
var source = -1
var sink = -1

/** 메모리 초기화 */
fun initMemory() {
    for (list in adjList) {
        list.clear()
    }
    for (arr in capacities) {
        Arrays.fill(arr, 0L)
    }
    for (arr in flows) {
        Arrays.fill(arr, 0L)
    }
    Arrays.fill(vertexLevels, NOT_ASSIGNED)
    Arrays.fill(idxToStartSearch, 0)
}

/** 간선 연결 */
fun connectEdge(a: Int, b: Int, capacity: Long) {
    capacities[a][b] = capacity
    adjList[a].add(b)
    adjList[b].add(a)
}

/** 디닉 알고리즘을 통해 max flow 를 구해서 리턴한다 */
fun getMaxFlow(): Long {
    var ret = 0L
    while (true) {
        setVertexLevelsByBFS()
        if (vertexLevels[sink] == NOT_ASSIGNED) {
            break
        }

        Arrays.fill(idxToStartSearch, 0)
        while (true) {
            val flow = findPathToSendFlowByDFS(source, INF)
            if (flow == 0L) {
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
fun findPathToSendFlowByDFS(cur: Int, bottleneck: Long): Long {
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