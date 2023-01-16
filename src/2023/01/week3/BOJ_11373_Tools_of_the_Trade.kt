import java.util.*
import kotlin.math.min

const val MAX_N = 26
const val MAX_M = 1000
const val MAX_V = MAX_N + MAX_M + 10
const val NOT_VISITED = -1
const val INF = 98765432109876543L

val adjList = Array(MAX_V + 1) { mutableListOf<Int>() }
val costs = Array(MAX_V + 1) { LongArray(MAX_V + 1) { 0L } }
val capacities = Array(MAX_V + 1) { LongArray(MAX_V + 1) { 0L } }
val flows = Array(MAX_V + 1) { LongArray(MAX_V + 1) { 0L } }
val distances = LongArray(MAX_V + 1) { INF }
val cameFrom = IntArray(MAX_V + 1) { NOT_VISITED }
val isInQueue = BooleanArray(MAX_V + 1) { false }

const val source = MAX_N + MAX_M + 2
const val sink = MAX_N + MAX_M + 3

data class Result(val minCost: Long, val maxFlow: Long)

fun getWeaponNum(idx: Int): Int {
    return idx
}

fun getSoldierNum(idx: Int): Int {
    return MAX_N + idx
}

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    val tests = readLine()!!.toInt()
    repeat(tests) {
        initMemory()

        val (n, m) = readLine().trim().split(" ").map { it.toInt() }
        for (i in 0 until n) {
            val st = StringTokenizer(readLine()!!.trim(), " ")
            val weapon = getWeaponNum(st.nextToken()[0].minus('A'))
            val capacity = st.nextToken().toLong()
            connectEdge(source, weapon, capacity, 0L)
        }

        for (i in 0 until m) {
            val line = readLine()!!
            for (k in line.indices) {
                val weapon= getWeaponNum(line[k].minus('A'))
                connectEdge(weapon, getSoldierNum(i), 1L, k.toLong())
            }
            connectEdge(getSoldierNum(i), sink, 1, 0L)
        }

        val mcmf = getMinCostMaxFlow()
        sb.append("${mcmf.minCost}\n")
    }

    print(sb)
}


/** 메모리 초기화 */
fun initMemory() {
    for (list in adjList) {
        list.clear()
    }
    for (arr in costs) {
        Arrays.fill(arr, 0L)
    }
    for (arr in capacities) {
        Arrays.fill(arr, 0L)
    }
    for (arr in flows) {
        Arrays.fill(arr, 0L)
    }
    Arrays.fill(distances, INF)
    Arrays.fill(cameFrom, NOT_VISITED)
    Arrays.fill(isInQueue, false)
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