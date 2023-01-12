import java.util.*

const val MAX_DOMAIN = 10000
const val MAX_RANGE = 100000
const val NOT_MATCHED = -1
const val INF = 987654321

var V = 0
val adjList = Array(MAX_DOMAIN + 1) { mutableListOf<Int>() }
val domainToRange = IntArray(MAX_DOMAIN + 1) { NOT_MATCHED }
val rangeToDomain = IntArray(MAX_RANGE + 1) { NOT_MATCHED }
val vertexLevels = IntArray(MAX_DOMAIN + 1) { INF }

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()
    while (true) {
        initMemory()

        V = (readLine() ?: break).toInt()
        repeat(V) {
            val st = StringTokenizer(readLine()!!.trim(), " ")
            val jobToken = st.nextToken()
            val jobNumber = jobToken.substring(0, jobToken.length - 1).toInt()

            val serverCntToken = st.nextToken()
            val serverCnt = serverCntToken.substring(1, serverCntToken.length - 1).toInt()
            repeat(serverCnt) {
                val serverNumber = st.nextToken().toInt()
                adjList[jobNumber].add(serverNumber)
            }
        }

        // 최대 매칭 계산
        val maxMatches = getMaxBipartiteMatches()

        sb.append("$maxMatches\n")
    }

    print(sb)

}

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
        for (v in 0 until V) {
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

    for (start in 0 until V) {
        if (domainToRange[start] == NOT_MATCHED) {
            vertexLevels[start] = 0
            q.offer(start)
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