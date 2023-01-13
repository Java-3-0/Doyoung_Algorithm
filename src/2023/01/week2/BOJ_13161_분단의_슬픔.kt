import java.util.*
import kotlin.math.min

const val MAX_V = 500 + 2
const val INF = 987654321
const val NOT_ASSIGNED = -1

val adjList = Array(MAX_V + 1) { mutableListOf<Int>() }
val capacities = Array(MAX_V + 1) { IntArray(MAX_V + 1) { 0 } }
val flows = Array(MAX_V + 1) { IntArray(MAX_V + 1) { 0 } }
val vertexLevels = IntArray(MAX_V + 1) { NOT_ASSIGNED }
val idxToStartSearch = IntArray(MAX_V + 1) { 0 }
var n = 0
var source = 0
var sink = 0

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    // 정점 개수 입력 받고, source, sink 생성
    n = readLine()!!.trim().toInt()
    source = n
    sink = n + 1

    // A 그룹에 속해야 하는 사람들을 source 에, B 그룹에 속해야 되는 사람들을 sink 에 연결한다
    val list = readLine()!!.trim().split(" ").map { it.toInt() }
    for (i in list.indices) {
        when (list[i]) {
            1 -> {
                capacities[source][i] = INF
                adjList[source].add(i)
                adjList[i].add(source)
            }

            2 -> {
                capacities[i][sink] = INF
                adjList[i].add(sink)
                adjList[sink].add(i)
            }
        }
    }

    // 사람들끼리 연결한다.
    for (y in 0 until n) {
        val st = StringTokenizer(readLine()!!.trim(), " ")
        for (x in 0 until n) {
            val sadness = st.nextToken().toInt()
            capacities[y][x] = sadness
            if (y != x) {
                adjList[y].add(x)
            }
        }
    }

    // maxFlow 를 구한다
    val maxFlow = getMaxFlow()

    // groupA 와 groupB 에 속하는 사람들을 탐색한다
    val groups = findDividedGroups()

    // 출력
    sb.append("$maxFlow\n")
    for (i in groups.first) {
        sb.append("$i ")
    }
    sb.append("\n")
    for (i in groups.second) {
        sb.append("$i ")
    }
    sb.append("\n")

    print(sb)
}

fun findDividedGroups(): Pair<MutableList<Int>, MutableList<Int>> {
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

    val listA = mutableListOf<Int>()
    val listB = mutableListOf<Int>()
    for (i in 0 until n) {
        if (isVisited[i]) {
            listA.add(i + 1)
        } else {
            listB.add(i + 1)
        }
    }

    return Pair(listA, listB)


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