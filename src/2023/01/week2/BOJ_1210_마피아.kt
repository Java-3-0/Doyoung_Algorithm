import java.util.*
import kotlin.math.min

const val MAX_N = 200
const val MAX_V = MAX_N * 2
const val INF = 987654321098765432L
const val NOT_ASSIGNED = -1

val adjList = Array(MAX_V + 1) { mutableListOf<Int>() }
val capacities = Array(MAX_V + 1) { LongArray(MAX_V + 1) { 0L } }
val flows = Array(MAX_V + 1) { LongArray(MAX_V + 1) { 0L } }
val vertexLevels = IntArray(MAX_V + 1) { NOT_ASSIGNED }
val idxToStartSearch = IntArray(MAX_V + 1) { 0 }
var N = 0
var E = 0
var source = 0
var sink = 0

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    // 톨게이트 수, 고속도로 수 입력
    var st = StringTokenizer(readLine()!!.trim(), " ")
    N = st.nextToken().toInt()
    E = st.nextToken().toInt()

    // source, sink 입력
    st = StringTokenizer(readLine()!!.trim(), " ")
    source = inNum(st.nextToken().toInt() - 1)
    sink = outNum(st.nextToken().toInt() - 1)

    // 정점분할 해서 들어오는 정점은 (i * 2), 나가는 정점은 (i * 2 + 1) 로 처리
    for (i in 0 until N) {
        val cost = readLine()!!.trim().toLong()

        val inVertexNum = inNum(i)
        val outVertexNum = outNum(i)

        capacities[inVertexNum][outVertexNum] = cost
        adjList[inVertexNum].add(outVertexNum)
        adjList[outVertexNum].add(inVertexNum)
    }

    // 고속도로 정보 입력
    for (i in 0 until E) {
        val (a, b) = readLine()!!.trim().split(" ").map { it.toInt() - 1 }
        val inA = inNum(a)
        val outA = outNum(a)
        val inB = inNum(b)
        val outB = outNum(b)

        capacities[outA][inB] = INF
        adjList[outA].add(inB)
        adjList[inB].add(outA)

        capacities[outB][inA] = INF
        adjList[outB].add(inA)
        adjList[inA].add(outB)
    }

    // maxFlow 를 구한다
    val maxFlow = getMaxFlow()

    // 정점 분할 내부 간선 중 잘라진 것들을 구한다
    val cutVertices = findCutVertices()
    for (v in cutVertices) {
        sb.append("$v ")
    }
    sb.append("\n")

    print(sb)
}

fun findCutVertices(): MutableList<Int> {
    val isVisited = BooleanArray(MAX_V) { false }
    val q = ArrayDeque<Int>()

    isVisited[source] = true
    q.offer(source)

    // max flow 가 min cut 으로 사용되므로, 용량이 남은 곳만 cut 되지 않은 간선으로 사용할 수 있다.
    while (q.isNotEmpty()) {
        val cur = q.poll()
        for (next in adjList[cur]) {
            if (!isVisited[next] && capacities[cur][next] - flows[cur][next] > 0) {
                isVisited[next] = true
                q.offer(next)
            }
        }
    }

    // 들어간 적은 있는데 나온 적이 없는 정점이면 끊긴 정점이다
    val ret = mutableListOf<Int>()
    for (i in 0 until N) {
        if (isVisited[inNum(i)] && !isVisited[outNum(i)]) {
            ret.add(i + 1)
        }
    }

    return ret
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

fun inNum(vertex: Int): Int {
    return vertex * 2
}

fun outNum(vertex: Int): Int {
    return vertex * 2 + 1
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