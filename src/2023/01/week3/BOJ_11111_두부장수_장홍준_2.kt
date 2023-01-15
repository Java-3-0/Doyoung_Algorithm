import java.util.*
import kotlin.math.min

const val MAX_H = 50
const val MAX_W = 50
const val MAX_V = MAX_H * MAX_W + 10
const val NOT_VISITED = -1
const val INF = 98765432109876543L

val pricesOfTofu = arrayOf(
    intArrayOf(10, 8, 7, 5, 1),
    intArrayOf(8, 6, 4, 3, 1),
    intArrayOf(7, 4, 3, 2, 1),
    intArrayOf(5, 3, 2, 2, 1),
    intArrayOf(1, 1, 1, 1, 0)
)

val grid = Array(MAX_H + 1) { IntArray(MAX_W + 1) { 0 } }
val adjList = Array(MAX_V + 1) { mutableListOf<Int>() }
val costs = Array(MAX_V + 1) { LongArray(MAX_V + 1) { 0L } }
val capacities = Array(MAX_V + 1) { LongArray(MAX_V + 1) { 0L } }
val flows = Array(MAX_V + 1) { LongArray(MAX_V + 1) { 0L } }
val distances = LongArray(MAX_V + 1) { INF }
val cameFrom = IntArray(MAX_V + 1) { NOT_VISITED }
val isInQueue = BooleanArray(MAX_V + 1) { false }

const val trash = MAX_H * MAX_W + 1
const val source = MAX_H * MAX_W + 2
const val sink = MAX_H * MAX_W + 3

var H = 0
var W = 0

data class Result(val minCost: Long, val maxFlow: Long)

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val st = StringTokenizer(readLine()!!.trim(), " ")
    H = st.nextToken().toInt()
    W = st.nextToken().toInt()

    for (y in 0 until H) {
        val line = readLine()!!
        for (x in 0 until W) {
            val c = line[x]
            grid[y][x] = if (c == 'F') (line[x].minus('A') - 1) else line[x].minus('A')
        }
    }

    for (y in 0 until H) {
        for (x in 0 until W) {
            val idx1 = y * W + x
            val isLeft = ((y + x) % 2 == 0)
            val ny = y + 1
            val nx = x + 1

            // 인접한 정점들 연결
            if (ny < H) {
                val idx2 = ny * W + x
                val price = pricesOfTofu[grid[y][x]][grid[ny][x]].toLong()
                if (isLeft) {
                    connectEdge(idx1, idx2, 1L, -price)
                } else {
                    connectEdge(idx2, idx1, 1L, -price)
                }
            }
            if (nx < W) {
                val idx3 = y * W + nx
                val price = pricesOfTofu[grid[y][x]][grid[y][nx]].toLong()
                if (isLeft) {
                    connectEdge(idx1, idx3, 1L, -price)
                } else {
                    connectEdge(idx3, idx1, 1L, -price)
                }
            }

            // 버려지는 것 연결
            if (isLeft) {
                connectEdge(idx1, trash, 1L, 0L)
            }

            // source 또는 sink 와 연결
            if (isLeft) {
                connectEdge(source, idx1, 1L, 0L)
            } else {
                connectEdge(idx1, sink, 1L, 0L)
            }
        }
    }

    // trash 와 sink 를 연결
    connectEdge(trash, sink, INF, 0L)

    val mcmf = getMinCostMaxFlow()
    val answer = -mcmf.minCost
    println(answer)
}

/** 간선 연결 */
fun connectEdge(from: Int, to: Int, capacity: Long, cost: Long) {
    capacities[from][to] += capacity
    costs[from][to] += cost
    costs[to][from] -= cost
    adjList[from].add(to)
    adjList[to].add(from)
}

/** 최소 비용 최대 유량 계산 */
fun getMinCostMaxFlow(): Result {
    var minCost = 0L
    var maxFlow = 0L

    while (true) {
        // 메모리 초기화
        Arrays.fill(distances, INF)
        Arrays.fill(cameFrom, NOT_VISITED)
        Arrays.fill(isInQueue, false)

        // 시작 정점 처리
        val q = LinkedList<Int>()
        distances[source] = 0L
        cameFrom[source] = source
        isInQueue[source] = true
        q.offer(source)

        // bfs 수행
        while (q.isNotEmpty()) {
            val now = q.poll()!!
            isInQueue[now] = false

            // 유량을 보낼 수 있고, 최소 비용을 갱신할 수 있는 정점 탐색
            for (next in adjList[now]) {
                if (flows[now][next] < capacities[now][next] && distances[now] + costs[now][next] < distances[next]) {
                    distances[next] = distances[now] + costs[now][next]
                    cameFrom[next] = now

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
            flow = min(flow, capacities[prev][cur] - flows[prev][cur])
            cur = prev
        }

        // 최대 유량만큼을 흘려 보낸다
        cur = sink
        while (cur != source) {
            val prev = cameFrom[cur]
            flows[prev][cur] += flow
            flows[cur][prev] -= flow
            minCost += flow * costs[prev][cur]
            cur = prev
        }

        maxFlow += flow
    }

    return Result(minCost, maxFlow)
}