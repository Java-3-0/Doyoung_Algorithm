import java.util.*
import kotlin.math.min

const val MAX_N = 50
const val MAX_V = 2 * MAX_N * MAX_N
const val NOT_VISITED = -1
const val INF = 98765432109876543L

val grid = Array(MAX_N + 1) { IntArray(MAX_N + 1) { 0 } }
val adjList = Array(MAX_V + 1) { mutableListOf<Edge>() }
val distances = LongArray(MAX_V + 1) { INF }
val cameFrom = IntArray(MAX_V + 1) { NOT_VISITED }
val usedEdgeIdx = IntArray(MAX_V + 1) { NOT_VISITED }
val isInQueue = BooleanArray(MAX_V + 1) { false }

var source = -1
var sink = -1
var N = 0

data class Edge(val to: Int, val revIdx: Int, var capacity: Long, val cost: Long)
data class Result(val minCost: Long, val maxFlow: Long)

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val (n, k) = readLine().trim().split(" ").map { it.toInt() }
    N = n
    source = getInNum(0)
    sink = getOutNum(n * n - 1)

    for (y in 0 until n) {
        val st = StringTokenizer(readLine()!!.trim(), " ")
        for (x in 0 until n) {
            grid[y][x] = st.nextToken().toInt()
        }
    }

    // 그래프상에서 이동할 수 있는 간선 생성
    for (y in 0 until n) {
        for (x in 0 until n) {
            val idx1 = y * n + x
            val outNum1 = getOutNum(idx1)

            if (y + 1 < n) {
                val idx2 = (y + 1) * n + x
                val inNum2 = getInNum(idx2)
                connectEdge(outNum1, inNum2, k.toLong(), 0)
            }

            if (x + 1 < n) {
                val idx3 = y * n + (x + 1)
                val inNum3 = getInNum(idx3)
                connectEdge(outNum1, inNum3, k.toLong(), 0)
            }
        }
    }

    // 정점분할 내부 간선 생성
    for (y in 0 until n) {
        for (x in 0 until n) {
            val idx = y * n + x
            val inNum = getInNum(idx)
            val outNum = getOutNum(idx)
            connectEdge(inNum, outNum, 1, -grid[y][x].toLong())
            connectEdge(inNum, outNum, (k - 1).toLong(), 0)
        }
    }

    // MCMF 찾기
    val result = getMinCostMaxFlow()
    val answer = -result.minCost
    println(answer)
}

fun getInNum(v: Int): Int {
    return v * 2
}

fun getOutNum(v: Int): Int {
    return v * 2 + 1
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

//            println("from: (${(prev / 2) / N}, ${(prev / 2) % N}) to: (${(cur / 2) / N}, ${(cur / 2) % N}) flow: $flow")

            minCost += flow * adjList[prev][edgeIdx].cost
            cur = prev
        }

        maxFlow += flow
    }

    return Result(minCost, maxFlow)
}