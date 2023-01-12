import java.util.*

const val MAX_DOMAIN = 1000
const val MAX_RANGE = 1000
const val NOT_MATCHED = -1
const val INF = 987654321

var DOMAIN = 0
var RANGE = 0
val adjList = Array(MAX_DOMAIN + 1) { mutableListOf<Int>() }
val domainToRange = IntArray(MAX_DOMAIN + 1) { NOT_MATCHED }
val rangeToDomain = IntArray(MAX_RANGE + 1) { NOT_MATCHED }
val vertexLevels = IntArray(MAX_DOMAIN + 1) { INF }
val isInLeftVertexCover = BooleanArray(MAX_DOMAIN + 1) { true }
val isInRightVertexCover = BooleanArray(MAX_RANGE + 1) { false }
val isVisitedLeft = BooleanArray(MAX_DOMAIN + 1) { false }
val isVisitedRight = BooleanArray(MAX_DOMAIN + 1) { false }

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()
    var st = StringTokenizer(readLine()!!.trim(), " ")
    DOMAIN = st.nextToken().toInt()
    RANGE = st.nextToken().toInt()
    for (from in 1 .. DOMAIN) {
        st = StringTokenizer(readLine()!!.trim(), " ")
        val cnt = st.nextToken().toInt()
        repeat(cnt) {
            val to = st.nextToken().toInt()
            adjList[from].add(to)
        }
    }

    // 이분 매칭으로 최대 매칭 수 계산
    val maxMatches = getMaxBipartiteMatches()

    // 실제 버텍스 커버를 찾기
    val vertexCover = findMinVertexCover()

    // 출력
    sb.append("$maxMatches\n")

    sb.append("${vertexCover.first.size} ")
    vertexCover.first.forEach { item -> sb.append("$item ") }
    sb.append("\n")

    sb.append("${vertexCover.second.size} ")
    vertexCover.second.forEach { item -> sb.append("$item ") }
    sb.append("\n")

    print(sb)

}

/** 최소 버텍스 커버 찾기 */
fun findMinVertexCover(): Pair<MutableList<Int>, MutableList<Int>> {
    for (v in 1 .. DOMAIN) {
        if (domainToRange[v] == NOT_MATCHED) {
            dfsAlternatingPath(v, true)
        }
    }

    val listA = mutableListOf<Int>()
    for (v in 1 .. DOMAIN) {
        if (isInLeftVertexCover[v]) {
            listA.add(v)
        }
    }

    val listB = mutableListOf<Int>()
    for (v in 1 .. RANGE) {
        if (isInRightVertexCover[v]) {
            listB.add(v)
        }
    }

    return Pair(listA, listB)
}

/** vertexNum 으로부터 alternating path 를 통해 방문할 수 있는 정점들을 DFS 탐색 */
fun dfsAlternatingPath(vertexNum: Int, isLeft: Boolean) {
    // domain 에서 range 쪽으로 매칭에 포함되지 않은 간선 탐색
    if (isLeft) {
        if (isVisitedLeft[vertexNum]) {
            return
        }

        isVisitedLeft[vertexNum] = true
        isInLeftVertexCover[vertexNum] = false

        for (y in adjList[vertexNum]) {
            if (domainToRange[vertexNum] != y) {
                dfsAlternatingPath(y, false)
            }
        }
    }

    // range 에서 domain 쪽으로 매칭에 포함된 간선 탐색
    else {
        if (isVisitedRight[vertexNum]) {
            return
        }

        isVisitedRight[vertexNum] = true
        isInRightVertexCover[vertexNum] = true

        val x = rangeToDomain[vertexNum]
        if (x != NOT_MATCHED) {
            dfsAlternatingPath(x, true)
        }
    }
}

/** 호프크로프트-카프: 이분매칭 최대 매칭 수 리턴 */
fun getMaxBipartiteMatches(): Int {
    var ret = 0

    while (true) {
        // 정점 레벨 매기기
        setVertexLevelsByBFS()

        // 새로운 매칭 시도
        var flowCnt = 0
        for (v in 1 .. DOMAIN) {
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

    for (start in 1 .. DOMAIN) {
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