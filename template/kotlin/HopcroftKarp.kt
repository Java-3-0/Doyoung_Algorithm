import java.util.*

const val MAX_DOMAIN = 1000
const val MAX_RANGE = 1000
const val NOT_MATCHED = -1
const val INF = 987654321

var DOMAIN = MAX_DOMAIN
var RANGE = MAX_RANGE

val adjList = Array(MAX_DOMAIN + 1) { mutableListOf<Int>() }
val domainToRange = IntArray(MAX_DOMAIN + 1) { NOT_MATCHED }
val rangeToDomain = IntArray(MAX_RANGE + 1) { NOT_MATCHED }
val vertexLevels = IntArray(MAX_DOMAIN + 1) { INF }

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
        setVertexLevelsByBFS()

        var flowCnt = 0
        for (v in 0 until DOMAIN) {
            if (domainToRange[v] == NOT_MATCHED && findMatchingByDFS(v)) {
                flowCnt++
            }
        }

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
