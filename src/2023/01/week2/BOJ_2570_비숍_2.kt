import java.util.*
import kotlin.math.abs

const val MAX_N = 100
const val MAX_V = MAX_N * MAX_N
const val NOT_MATCHED = -1
const val EMPTY = 0
const val BLOCKED = 1
const val INF = 987654321

var N = 0
var M = 0
var V = 0

val grid = Array(MAX_N + 1) { IntArray(MAX_N + 1) { EMPTY } }

var groupCntRD = 0
var groupCntLD = 0
val groupNumberRD = Array(MAX_N + 1) { IntArray(MAX_N + 1) { -1 } }
val groupNumberLD = Array(MAX_N + 1) { IntArray(MAX_N + 1) { -1 } }

val adjList = Array(MAX_V + 1) { mutableListOf<Int>() }
val domainToRange = IntArray(MAX_V + 1) { NOT_MATCHED }
val rangeToDomain = IntArray(MAX_V + 1) { NOT_MATCHED }
val vertexLevels = IntArray(MAX_V + 1) { 0 }

data class Position(val y: Int, val x: Int)

fun main(): Unit = with(System.`in`.bufferedReader()) {
    // 체스판 크기, 장애물 개수 입력
    N = readLine()!!.toInt()
    M = readLine()!!.toInt()

    // 정점 수
    V = N * N

    // 장애물 위치 입력받아서 grid 갱신
    repeat(M) {
        val (y, x) = readLine()!!.split(" ").map { it.toInt() - 1 }
        grid[y][x] = BLOCKED
    }

    // 오른쪽 아래 대각선 방향으로 서로 공격 가능한 것들끼리 같은 groupNumberRD 값으로 묶음
    doGroupingRightDown()

    // 왼쪽 아래 대각선 방향으로 서로 공격 가능한 것들끼리 같은 groupNumberRD 값으로 묶음
    doGroupingLeftDown()

    // 간선 생성
    for (y in 0 until N) {
        for (x in 0 until N) {
            if (grid[y][x] == EMPTY) {
                adjList[groupNumberRD[y][x]].add(groupNumberLD[y][x])
            }
        }
    }

    // 이분 매칭 수행
    val answer = getMaxBipartiteMatches()
    println(answer)
}

// 오른쪽 아래 대각선 방향으로 서로 공격 가능한 것들끼리 같은 groupNumberRD 값으로 묶음
fun doGroupingRightDown() {
    for (delta in -(N - 1) until N) {
        val startY = if (delta >= 0) 0 else abs(delta)
        val startX = if (delta >= 0) abs(delta) else 0

        val positionList = mutableListOf<Position>()
        for (distance in 0 until N - abs(delta)) {
            val y = startY + distance
            val x = startX + distance

            if (grid[y][x] == EMPTY) {
                positionList.add(Position(y, x))
            } else {
                if (positionList.isNotEmpty()) {
                    groupCntRD++
                    for (position in positionList) {
                        groupNumberRD[position.y][position.x] = groupCntRD
                    }
                    positionList.clear()
                }
            }
        }

        if (positionList.isNotEmpty()) {
            groupCntRD++
            for (position in positionList) {
                groupNumberRD[position.y][position.x] = groupCntRD
            }
            positionList.clear()
        }
    }
}

// 왼쪽 아래 대각선 방향으로 서로 공격 가능한 것들끼리 같은 groupNumberRD 값으로 묶음
fun doGroupingLeftDown() {
    for (delta in -(N - 1) until N) {
        val startY = if (delta >= 0) 0 else abs(delta)
        val startX = if (delta >= 0) N - 1 - abs(delta) else N - 1

        val positionList = mutableListOf<Position>()
        for (distance in 0 until N - abs(delta)) {
            val y = startY + distance
            val x = startX - distance

            if (grid[y][x] == EMPTY) {
                positionList.add(Position(y, x))
            } else {
                if (positionList.isNotEmpty()) {
                    groupCntLD++
                    for (position in positionList) {
                        groupNumberLD[position.y][position.x] = groupCntLD
                    }
                    positionList.clear()
                }
            }
        }

        if (positionList.isNotEmpty()) {
            groupCntLD++
            for (position in positionList) {
                groupNumberLD[position.y][position.x] = groupCntLD
            }
            positionList.clear()
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
        for (v in 1 .. groupCntRD) {
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

    for (start in 1 .. groupCntRD) {
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