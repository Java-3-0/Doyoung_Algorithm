import java.util.*

const val MAX_H = 50
const val MAX_W = 50
const val MAX_DOMAIN = MAX_H * MAX_W
const val MAX_RANGE = MAX_H * MAX_W
const val NOT_MATCHED = -1
const val INF = 987654321
const val NO_HOLE = '.'
const val HOLE = '*'

var H = 0
var W = 0
var DOMAIN = 0
var RANGE = 0

val grid = Array(MAX_H + 1) { CharArray(MAX_W + 1) { NO_HOLE } }
var horizontalGroupCnt = 0
var verticalGroupCnt = 0
val horizontalGroupNum = Array(MAX_H + 1) { IntArray(MAX_W + 1) { -1 } }
val verticalGroupNum = Array(MAX_H + 1) { IntArray(MAX_W + 1) { -1 } }

val adjList = Array(MAX_DOMAIN + 1) { mutableListOf<Int>() }
val domainToRange = IntArray(MAX_DOMAIN + 1) { NOT_MATCHED }
val rangeToDomain = IntArray(MAX_RANGE + 1) { NOT_MATCHED }
val vertexLevels = IntArray(MAX_DOMAIN + 1) { INF }

fun main(): Unit = with(System.`in`.bufferedReader()) {
    var st = StringTokenizer(readLine()!!.trim(), " ")
    H = st.nextToken().toInt()
    W = st.nextToken().toInt()

    for (y in 0 until H) {
        val line = readLine()!!
        for (x in 0 until W) {
            grid[y][x] = line[x]
        }
    }

    // 구멍이 이어진 위치들끼리 그룹핑
    setGroupsHorizontally()
    setGroupsVertically()
    DOMAIN = horizontalGroupCnt
    RANGE = verticalGroupCnt

    // 칸마다 가로 그룹 번호 -> 세로 그룹 번호로 간선 생성
    for (y in 0 until H) {
        for (x in 0 until W) {
            if (grid[y][x] == HOLE) {
                adjList[horizontalGroupNum[y][x]].add(verticalGroupNum[y][x])
            }
        }
    }

    // 그룹 중 일부를 선택해서 가로 세로의 모든 그룹을 커버해야 하므로, 필요한 값은 최소 버텍스 커버
    // 최소 버텍스 커버 = 최대 매칭
    val answer = getMaxBipartiteMatches()
    println(answer)
}

/** 가로로 이어진 구멍 그룹핑 */
fun setGroupsHorizontally() {
    for (y in 0 until H) {
        val listX = mutableListOf<Int>()
        for (x in 0 until W + 1) {
            if (x < W && grid[y][x] == HOLE) {
                listX.add(x)
            } else {
                if (listX.isNotEmpty()) {
                    horizontalGroupCnt++
                    for (num in listX) {
                        horizontalGroupNum[y][num] = horizontalGroupCnt
                    }
                    listX.clear()
                }
            }
        }
    }
}

/** 세로로 이어진 구멍 그룹핑 */
fun setGroupsVertically() {
    for (x in 0 until W) {
        val listY = mutableListOf<Int>()
        for (y in 0 until H + 1) {
            if (y < H && grid[y][x] == HOLE) {
                listY.add(y)
            } else {
                if (listY.isNotEmpty()) {
                    verticalGroupCnt++
                    for (num in listY) {
                        verticalGroupNum[num][x] = verticalGroupCnt
                    }
                    listY.clear()
                }
            }
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