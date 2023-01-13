import java.util.*

const val MAX_V = 1000
const val MAX_E = 5000
const val MAX_DOMAIN = MAX_V
const val MAX_RANGE = MAX_V
const val NOT_MATCHED = -1
const val INF = 987654321

var DOMAIN = MAX_DOMAIN
var RANGE = MAX_RANGE
var V = 0
var E = 0

val adjList = Array(MAX_DOMAIN + 1) { mutableListOf<Int>() }
val domainToRange = IntArray(MAX_DOMAIN + 1) { NOT_MATCHED }
val rangeToDomain = IntArray(MAX_RANGE + 1) { NOT_MATCHED }
val vertexLevels = IntArray(MAX_DOMAIN + 1) { INF }

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    val st = StringTokenizer(readLine()!!.trim(), " ")
    V = st.nextToken().toInt()
    E = st.nextToken().toInt()

    DOMAIN = V
    RANGE = V

    repeat(E) {
        val (a, b) = readLine().trim().split(" ").map { it.toInt() - 1 }
        adjList[a].add(b)
        adjList[b].add(a)
    }

    val maxMatches = getMaxBipartiteMatches()

    if (maxMatches == V) {
        for (i in 0 until DOMAIN) {
            sb.append("${domainToRange[i] + 1}\n")
        }
    } else {
        sb.append("Impossible\n")
    }

    print(sb)
}

/** 메모리 초기화 */
fun initMemory() {
    for (list in adjList) {
        list.clear()
    }
    Arrays.fill(domainToRange, NOT_MATCHED)
    Arrays.fill(rangeToDomain, NOT_MATCHED)
    Arrays.fill(vertexLevels, INF)
}

/** 호프크로프트-카프: 이분매칭 최대 매칭 수 리턴 */
fun getMaxBipartiteMatches(): Int {
    var ret = 0

    while (true) {
        // 정점 레벨 매기기
        setVertexLevelsByBFS()

        // 새로운 매칭 시도
        var flowCnt = 0
        for (v in 0 until DOMAIN) {
            if (domainToRange[v] == NOT_MATCHED && findMatchingByDFS(v)) {
                flowCnt++
            }
        }

        // 더 이상 새로운 매칭이 없으면 종료, 새로운 매칭이 있으면 개수를 합산하고 계속 진행
        if (flowCnt == 0) {
            break
        } else {
            ret += flowCnt
        }
    }

    return ret
}

/** 호프크로프트-카프: Domain 의 각 정점에 레벨을 매긴다 */
fun setVertexLevelsByBFS() {
    val q = LinkedList<Int>()
    Arrays.fill(vertexLevels, INF)

    for (v in 0 until DOMAIN) {
        if (domainToRange[v] == NOT_MATCHED) {
            vertexLevels[v] = 0
            q.offer(v)
        }
    }

    while (q.isNotEmpty()) {
        val cur = q.poll()
        for (adj in adjList[cur]) {
            val next = rangeToDomain[adj]
            if (next != NOT_MATCHED && vertexLevels[next] == INF) {
                vertexLevels[next] = vertexLevels[cur] + 1
                q.offer(next)
            }
        }
    }
}


/** 호프크로프트-카프: dfs 를 수행하면서 매칭을 찾는다 */
fun findMatchingByDFS(from: Int): Boolean {
    for (to in adjList[from]) {
        if (rangeToDomain[to] == NOT_MATCHED || (vertexLevels[rangeToDomain[to]] == vertexLevels[from] + 1 && findMatchingByDFS(rangeToDomain[to]))) {
            domainToRange[from] = to
            rangeToDomain[to] = from
            return true
        }
    }

    return false
}