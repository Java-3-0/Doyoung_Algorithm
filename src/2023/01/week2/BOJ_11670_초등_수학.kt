import java.util.*

const val MAX_DOMAIN = 2500
const val NOT_MATCHED_DOMAIN = -98765432109876543L
const val NOT_MATCHED_RANGE = -1
const val INF = 987654321

var DOMAIN = 0
val adjSet = Array(MAX_DOMAIN + 1) { hashSetOf<Long>() }
val domainToRange = LongArray(MAX_DOMAIN + 1) { NOT_MATCHED_DOMAIN }
val rangeToDomain = HashMap<Long, Int>()
val vertexLevels = IntArray(MAX_DOMAIN + 1) { INF }

val pairList = mutableListOf<Pair<Long, Long>>()

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    DOMAIN = readLine()!!.toInt()

    for (i in 0 until DOMAIN) {
        val (a, b) = readLine()!!.trim().split(" ").map { it.toLong() }
        pairList.add(Pair(a, b))

        adjSet[i].add(a + b)
        adjSet[i].add(a - b)
        adjSet[i].add(a * b)
    }

    val maxMatches = getMaxBipartiteMatches()
    if (maxMatches == DOMAIN) {
        for (i in 0 until DOMAIN) {
            val a = pairList[i].first
            val b = pairList[i].second
            val result = domainToRange[i]

            sb.append("$a")
            when (result) {
                a + b -> {
                    sb.append(" + ")
                }
                a - b -> {
                    sb.append(" - ")
                }
                a * b -> {
                    sb.append(" * ")
                }
            }
            sb.append("$b = $result")
            sb.append("\n")
        }
    } else {
        sb.append("Impossible\n")
    }

    print(sb)


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
            if (domainToRange[v] == NOT_MATCHED_DOMAIN && findMatchingByDFS(v)) {
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

    for (start in 0 until DOMAIN) {
        if (domainToRange[start] == NOT_MATCHED_DOMAIN) {
            vertexLevels[start] = 0
            q.offer(start)
        }
    }

    while (q.isNotEmpty()) {
        val cur = q.poll()
        for (adj in adjSet[cur]) {
            val next = rangeToDomain.getOrDefault(adj, NOT_MATCHED_RANGE)
            if (next != NOT_MATCHED_RANGE && vertexLevels[next] == INF) {
                vertexLevels[next] = vertexLevels[cur] + 1
                q.offer(next)
            }
        }
    }
}


/** 호프크로프트-카프: dfs 를 수행하면서 매칭을 찾는다 */
fun findMatchingByDFS(from: Int): Boolean {
    for (to in adjSet[from]) {
        val x = rangeToDomain.getOrDefault(to, NOT_MATCHED_RANGE)
        if (x == NOT_MATCHED_RANGE || (vertexLevels[x] == vertexLevels[from] + 1 && findMatchingByDFS(x))) {
            domainToRange[from] = to
            rangeToDomain[to] = from
            return true
        }
    }

    return false
}