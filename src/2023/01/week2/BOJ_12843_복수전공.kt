import java.util.*

const val MAX_DOMAIN = 2000
const val MAX_RANGE = 2000
const val NOT_MATCHED = -1
const val INF = 987654321

var DOMAIN = 0
var RANGE = 0
val adjList = Array(MAX_DOMAIN + 1) { mutableListOf<Int>() }
val domainToRange = IntArray(MAX_DOMAIN + 1) { NOT_MATCHED }
val rangeToDomain = IntArray(MAX_RANGE + 1) { NOT_MATCHED }
val vertexLevels = IntArray(MAX_DOMAIN + 1) { INF }
val isComputerClass = BooleanArray(MAX_DOMAIN + 1) { false }

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val (V, E) = readLine()!!.split(" ").map { it.toInt() }
    DOMAIN = V
    RANGE = V

    // 정점 중 컴퓨터 학부 과목인 것들을 domain 으로, 소프트웨어 학부 과목인 것들을 range 로
    repeat(V) {
        val tokens = readLine()!!.split(" ")

        val num = tokens[0].toInt()
        if (tokens[1][0] == 'c') {
            isComputerClass[num] = true
        }
    }

    // 간선 입력
    repeat(E) {
        val (a, b) = readLine()!!.split(" ").map { it.toInt() }
        if (isComputerClass[a]) {
            adjList[a].add(b)
        } else {
            adjList[b].add(a)
        }
    }

    // 최대 매칭
    val maxMatches = getMaxBipartiteMatches()

    // 최대 독립 집합
    val answer = V - maxMatches
    println(answer)

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
    val q = ArrayDeque<Int>()
    for (start in 1 .. DOMAIN) {
        if (domainToRange[start] == NOT_MATCHED) {
            vertexLevels[start] = 0
            q.offer(start)
        } else {
            vertexLevels[start] = INF
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