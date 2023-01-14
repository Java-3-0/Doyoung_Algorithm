import java.util.*
import kotlin.math.min

const val MAX_V = 1000
const val NOT_VISITED = -1
const val INF = 98765432109876543L

val adjList = Array(MAX_V + 1) { mutableListOf<Edge>() }
val distances = LongArray(MAX_V + 1) { INF }
val cameFrom = IntArray(MAX_V + 1) { NOT_VISITED }
val usedEdgeIdx = IntArray(MAX_V + 1) { NOT_VISITED }
val isInQueue = BooleanArray(MAX_V + 1) { false }

var source = -1
var sink = -1

data class Edge(val to: Int, val revIdx: Int, var capacity: Long, val cost: Long)
data class Result(val minCost: Long, val maxFlow: Long)

/** 메모리 초기화 */
fun initMemory() {
    for (list in adjList) {
        list.clear()
    }

    Arrays.fill(distances, INF)
    Arrays.fill(cameFrom, NOT_VISITED)
    Arrays.fill(usedEdgeIdx, NOT_VISITED)
    Arrays.fill(isInQueue, false)
}

/** 간선 연결 */
fun connectEdge(from: Int, to: Int, capacity: Long, cost: Long) {
    // 나중에 잔여 용량을 갱신할 때, 역방향 간선을 찾기 위해서 revIdx 를 저장해 둠
    adjList[from].add(Edge(to, adjList[to].size, capacity, cost))
    adjList[to].add(Edge(from, adjList[from].size - 1, 0, -cost))
}

/** 최소 비용 최대 유량 계산 */
fun getMinCostMaxFlow(): Result {
    var minCost = 0L
    var maxFlow = 0L

    while (true) {
        // 메모리 초기화
        Arrays.fill(distances, INF)
        Arrays.fill(cameFrom, NOT_VISITED)
        Arrays.fill(usedEdgeIdx, NOT_VISITED)
        Arrays.fill(isInQueue, false)

        // 시작 정점 처리
        val q = LinkedList<Int>()
        distances[source] = 0L
        cameFrom[source] = source
        usedEdgeIdx[source] = 0
        isInQueue[source] = true
        q.offer(source)

        // bfs 수행
        while (q.isNotEmpty()) {
            val now = q.poll()!!
            isInQueue[now] = false

            // 유량을 보낼 수 있고, 최소 비용을 갱신할 수 있는 정점 탐색
            for (i in adjList[now].indices) {
                val edge = adjList[now][i]
                val next = edge.to
                if (edge.capacity > 0 && distances[now] + edge.cost < distances[edge.to]) {
                    distances[next] = distances[now] + edge.cost
                    cameFrom[next] = now
                    usedEdgeIdx[next] = i
                    if (!isInQueue[next]) {
                        isInQueue[next] = true
                        q.offer(next)
                    }
                }
            }
        }

        // 도착점까지 가지 못한 경우 무한루프 종료
        if (cameFrom[sink] == NOT_VISITED) {
            break
        }

        // 도착점까지 간 경우 최대 유량 계산
        var flow = INF
        var cur = sink
        while (cur != source) {
            val prev = cameFrom[cur]
            val edgeIdx = usedEdgeIdx[cur]
            flow = min(flow, adjList[prev][edgeIdx].capacity)
            cur = prev
        }

        // 최대 유량만큼을 흘려 보낸다
        cur = sink
        while (cur != source) {
            val prev = cameFrom[cur]
            val edgeIdx = usedEdgeIdx[cur]
            adjList[prev][edgeIdx].capacity -= flow
            adjList[cur][adjList[prev][edgeIdx].revIdx].capacity += flow

            minCost += flow * adjList[prev][edgeIdx].cost
            cur = prev
        }

        maxFlow += flow
    }

    return Result(minCost, maxFlow)
}