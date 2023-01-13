import java.util.*
import kotlin.math.min

const val MAX_N = 10000
const val lab1 = MAX_N + 1
const val lab2 = MAX_N + 2
const val lab3 = MAX_N + 3
const val source = MAX_N + 4
const val sink = MAX_N + 5
const val MAX_V = MAX_N + 3 + 2
const val INF = 987654321
const val NOT_ASSIGNED = -1

val adjList = Array(MAX_V + 1) { mutableListOf<Int>() }
val capacities = Array(MAX_V + 1) { HashMap<Int, Int>() }
val flows = Array(MAX_V + 1) { HashMap<Int, Int>() }
val vertexLevels = IntArray(MAX_V + 1) { NOT_ASSIGNED }
val idxToStartSearch = IntArray(MAX_V + 1) { 0 }

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    val tests = readLine()!!.toInt()
    repeat(tests) {
        initMemory()
        val n = readLine()!!.trim().toInt()
        val (a, b, c) = readLine().trim().split(" ").map { it.toInt() }

        // 연구실을 source 와 연결
        connectEdge(source, lab1, a)
        connectEdge(source, lab2, b)
        connectEdge(source, lab3, c)

        // 사람들과 sink 를 연결
        for (person in 1 .. n) {
            connectEdge(person, sink, 1)
        }

        for (lab in lab1 .. lab3) {
            val st = StringTokenizer(readLine()!!.trim(), " ")
            val cnt = st.nextToken().toInt();

            // 사람들과 연구실을 연결
            repeat(cnt) {
                val person = st.nextToken().toInt()
                connectEdge(lab, person, 1)
            }
        }

        val maxFlow = getMaxFlow()
        sb.append("$maxFlow\n")
        for (person in 1 .. n) {
            for (k in adjList[person]) {
                if (k == lab1 || k == lab2 || k == lab3) {
                    if (flows[k][person]!! > 0) {
                        sb.append("$person ${'A'.plus(k - MAX_N - 1)}\n")
                    }
                }
            }
        }
    }

    print(sb)
}

fun connectEdge(a: Int, b: Int, cap: Int) {
    capacities[a][b] = cap
    adjList[a].add(b)
    adjList[b].add(a)
}

fun initMemory() {
    for (list in adjList) {
        list.clear()
    }
    for (map in capacities) {
        map.clear()
    }
    for (map in flows) {
        map.clear()
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
                if (vertexLevels[next] == NOT_ASSIGNED && capacities[cur][next]!! - flows[cur][next]!! > 0) {
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
        val remainingCapacity = capacities[cur][next]!! - flows[cur][next]!!
        if (vertexLevels[next] == vertexLevels[cur] + 1 && remainingCapacity > 0) {
            val flowToSend = findPathToSendFlowByDFS(next, min(bottleneck, remainingCapacity))
            if (flowToSend > 0) {
                flows[cur][next] = flows[cur][next]!! + flowToSend
                flows[next][cur] = flows[next][cur]!! - flowToSend
                return flowToSend
            }
        }

        idxToStartSearch[cur]++
    }

    return 0
}
