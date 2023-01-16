import java.util.*
import kotlin.math.min

const val MAX_COWS = 100
const val MAX_FOODS = 100
const val MAX_DRINKS = 100
const val MAX_V = MAX_COWS * 2 + MAX_FOODS + MAX_DRINKS + 10
const val INF = 98765432109876543L
const val NOT_VISITED = -1

val adjList = Array(MAX_V + 1) { mutableListOf<Int>() }
val capacities = Array(MAX_V + 1) { LongArray(MAX_V + 1) { 0L } }
val flows = Array(MAX_V + 1) { LongArray(MAX_V + 1) { 0L } }
val cameFrom = IntArray(MAX_V + 1) { NOT_VISITED }

const val source = MAX_COWS * 2 + MAX_FOODS + MAX_DRINKS + 2
const val sink = MAX_COWS * 2 + MAX_FOODS + MAX_DRINKS + 3

fun getInCowVertex(cowIdx: Int): Int {
    return cowIdx
}

fun getOutCowVertex(cowIdx: Int): Int {
    return MAX_COWS + cowIdx
}

fun getFoodVertex(foodIdx: Int): Int {
    return MAX_COWS * 2 + foodIdx
}

fun getDrinkVertex(drinkIdx: Int): Int {
    return MAX_COWS * 2 + MAX_FOODS + drinkIdx
}

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val (n, f, d) = readLine().trim().split(" ").map { it.toInt() }

    // source -> food -> inCow -> outCow -> drink -> sink 로 그래프 연결
    for (cowIdx in 0 until n) {
        val st = StringTokenizer(readLine()!!.trim(), " ")
        val foodCnt = st.nextToken().toInt()
        val drinkCnt = st.nextToken().toInt()

        // food -> inCow
        repeat(foodCnt) {
            val foodIdx = st.nextToken().toInt() - 1
            connectEdge(getFoodVertex(foodIdx), getInCowVertex(cowIdx), 1L)
        }

        // inCow -> outCow
        connectEdge(getInCowVertex(cowIdx), getOutCowVertex(cowIdx), 1L)

        // outCow -> drink
        repeat(drinkCnt) {
            val drinkIdx = st.nextToken().toInt() - 1
            connectEdge(getOutCowVertex(cowIdx), getDrinkVertex(drinkIdx),1L)
        }
    }

    // source -> food
    for (foodIdx in 0 until f) {
        connectEdge(source, getFoodVertex(foodIdx), 1L)
    }

    // drink -> sink
    for (drinkIdx in 0 until d) {
        connectEdge(getDrinkVertex(drinkIdx), sink, 1L)
    }

    val answer = getMaxFlow()
    println(answer)
}


/** 간선 연결 */
fun connectEdge(a: Int, b: Int, capacity: Long) {
    capacities[a][b] = capacity
    adjList[a].add(b)
    adjList[b].add(a)
}

/** source 에서 sink 로 보낼 수 있는 최대 유량을 구한다 */
fun getMaxFlow(): Long {
    var maxFlow = 0L

    while (true) {
        // 메모리 초기화
        Arrays.fill(cameFrom, NOT_VISITED)

        // BFS 초기 설정
        val q: Queue<Int> = LinkedList()
        cameFrom[source] = source
        q.offer(source)

        // BFS 수행해서 유량을 보낼 수 있는 경로 찾기
        while (!q.isEmpty()) {
            val now = q.poll()

            for (next in adjList[now]) {
                if (cameFrom[next] == NOT_VISITED && flows[now][next] < capacities[now][next]) {
                    cameFrom[next] = now
                    q.offer(next)
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
            cur = prev
        }
        maxFlow += flow
    }

    return maxFlow
}