import java.util.*
import kotlin.math.min

const val MAX_N = 500
const val MAX_V = MAX_N + 10
const val CATEGORY1 = MAX_N + 1
const val CATEGORY2 = MAX_N + 2
const val CATEGORY3 = MAX_N + 3
const val CATEGORY_NONE = MAX_N + 4
const val SRC = MAX_N + 5
const val SINK = MAX_N + 6
const val DUMMY = MAX_N + 7

const val NOT_VISITED = -1
const val BIG_COST = 10000000000L
const val INF = 98765432109876543L

val adjList = Array(MAX_V + 1) { mutableListOf<Int>() }
val costs = Array(MAX_V + 1) { LongArray(MAX_V + 1) { 0L } }
val capacities = Array(MAX_V + 1) { LongArray(MAX_V + 1) { 0L } }
val flows = Array(MAX_V + 1) { LongArray(MAX_V + 1) { 0L } }
val distances = LongArray(MAX_V + 1) { INF }
val cameFrom = IntArray(MAX_V + 1) { NOT_VISITED }
val isInQueue = BooleanArray(MAX_V + 1) { false }

data class Result(val minCost: Long, val maxFlow: Long)

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val n = readLine()!!.trim().toInt()

    // source 와 category 들을 연결
    val (A, B, C) = readLine()!!.trim().split(" ").map { it.toInt() }
    connectEdge(SRC, CATEGORY1, (A - 1).toLong(), 0L)
    connectEdge(SRC, CATEGORY2, (B - 1).toLong(), 0L)
    connectEdge(SRC, CATEGORY3, (C - 1).toLong(), 0L)
    connectEdge(SRC, CATEGORY_NONE, INF, 0L)

    // 가상의 정점 연결
    connectEdge(SRC, DUMMY, 3, -BIG_COST)
    connectEdge(DUMMY, CATEGORY1, 1, 0L)
    connectEdge(DUMMY, CATEGORY2, 1, 0L)
    connectEdge(DUMMY, CATEGORY3, 1, 0L)

    for (i in 0 until n) {
        val (a, b, c) = readLine()!!.trim().split(" ").map { it.toLong() }

        // category 들과 story 들을 연결
        connectEdge(CATEGORY1, i, 1L, -a)
        connectEdge(CATEGORY2, i, 1L, -b)
        connectEdge(CATEGORY3, i, 1L, -c)
        connectEdge(CATEGORY_NONE, i, 1L, 0L)

        // story 를 sink 에 연결
        connectEdge(i, SINK, 1L, 0L)
    }

    val mcmf = getMinCostMaxFlow()
    val answer = -(mcmf.minCost + 3L * BIG_COST)
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
        distances[SRC] = 0L
        cameFrom[SRC] = SRC
        isInQueue[SRC] = true
        q.offer(SRC)

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
        if (cameFrom[SINK] == NOT_VISITED) {
            break
        }

        // 도착점까지 간 경우 최대 유량 계산
        var flow = INF
        var cur = SINK
        while (cur != SRC) {
            val prev = cameFrom[cur]
            flow = min(flow, capacities[prev][cur] - flows[prev][cur])
            cur = prev
        }

        // 최대 유량만큼을 흘려 보낸다
        cur = SINK
        while (cur != SRC) {
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