import java.util.*
import kotlin.math.sqrt

const val MAX_DOMAIN = 100
const val MAX_RANGE = 100
const val NOT_MATCHED = -1
const val INF = 987654321

var DOMAIN = 0
var RANGE = 0
var TIME_LIMIT = 0
var SPEED = 0

val adjList = Array(MAX_DOMAIN + 1) { mutableListOf<Int>() }
val domainToRange = IntArray(MAX_DOMAIN + 1) { NOT_MATCHED }
val rangeToDomain = IntArray(MAX_RANGE + 1) { NOT_MATCHED }
val vertexLevels = IntArray(MAX_DOMAIN + 1) { INF }

data class Position(val y: Double, val x: Double) {
    fun getDistanceTo(position: Position): Double {
        val dy = this.y - position.y
        val dx = this.x - position.x
        return sqrt(dy * dy + dx * dx)
    }
}

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    while (true) {
        initMemory()

        val st = StringTokenizer((readLine() ?: break).trim(), " ")
        DOMAIN = st.nextToken().toInt()
        RANGE = st.nextToken().toInt()
        TIME_LIMIT = st.nextToken().toInt()
        SPEED = st.nextToken().toInt()

        val golferList = mutableListOf<Position>()
        repeat(DOMAIN) {
            val (x, y) = readLine()!!.trim().split(" ").map { it.toDouble() }
            golferList.add(Position(y, x))
        }

        val holeList = mutableListOf<Position>()
        repeat(RANGE) {
            val (x, y) = readLine()!!.trim().split(" ").map { it.toDouble() }
            holeList.add(Position(y, x))
        }

        for (i in 0 until DOMAIN) {
            for (j in 0 until RANGE) {
                val distance = golferList[i].getDistanceTo(holeList[j])
                if (distance <= TIME_LIMIT * SPEED) {
                    adjList[i].add(j)
                }
            }
        }

        val maxMatches = getMaxBipartiteMatches()
        val answer = DOMAIN - maxMatches
        sb.append("$answer\n")
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

    for (start in 0 until DOMAIN) {
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