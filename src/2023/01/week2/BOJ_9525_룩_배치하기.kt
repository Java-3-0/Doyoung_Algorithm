import java.util.*

const val MAX_N = 100
const val MAX_V = MAX_N * MAX_N
const val NOT_MATCHED = -1
const val EMPTY = '.'
const val BLOCK = 'X'

var N = 0
var V = 0

val grid = Array(MAX_N + 1) { CharArray(MAX_N + 1) { EMPTY } }

var horizontalGroupCnt = 0
var verticalGroupCnt = 0
val horizontalGroupNum = Array(MAX_N + 1) { IntArray(MAX_N + 1) { -1 } }
val verticalGroupNum = Array(MAX_N + 1) { IntArray(MAX_N + 1) { -1 } }

val adjList = Array(MAX_V + 1) { mutableListOf<Int>() }
val isChecked = BooleanArray(MAX_V + 1) { false }
val isMatchedTo = IntArray(MAX_V + 1) { NOT_MATCHED }

fun main(): Unit = with(System.`in`.bufferedReader()) {
    // 체스판 크기 입력
    N = readLine()!!.toInt()

    // 정점 수
    V = N * N

    for (y in 0 until N) {
        val line = readLine()!!
        for (x in 0 until N) {
            grid[y][x] = line[x]
        }
    }

    // 가로로 공격 가능한 것들끼리 그룹으로 묶음
    for (y in 0 until N) {
        val listX = mutableListOf<Int>()
        for (x in 0 until N + 1) {
            if (x < N && grid[y][x] == EMPTY) {
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

    // 세로로 공격 가능한 것끼리 그룹으로 묶음
    for (x in 0 until N) {
        val listY = mutableListOf<Int>()
        for (y in 0 until N + 1) {
            if (y < N && grid[y][x] == EMPTY) {
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

    // 칸마다 가로 그룹 번호 -> 세로 그룹 번호로 간선 생성
    for (y in 0 until N) {
        for (x in 0 until N) {
            if (grid[y][x] == EMPTY) {
                adjList[horizontalGroupNum[y][x]].add(verticalGroupNum[y][x])
            }
        }
    }

    // 이분 매칭 수행
    val answer = getMaxMatches()
    println(answer)
}

/** 이분매칭 최대 매칭 수 리턴 */
fun getMaxMatches(): Int {
    var ret = 0

    for (from in 1 .. horizontalGroupCnt) {
        Arrays.fill(isChecked, false)
        if (dfs(from)) {
            ret++
        }
    }

    return ret
}


/** dfs 를 수행하면서 매칭 시도  */
fun dfs(from: Int): Boolean {
    for (to in adjList[from]) {
        if (!isChecked[to]) {
            isChecked[to] = true
            if (isMatchedTo[to] == NOT_MATCHED || dfs(isMatchedTo[to])) {
                isMatchedTo[to] = from
                return true
            }
        }
    }

    return false
}