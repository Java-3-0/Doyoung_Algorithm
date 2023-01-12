import java.util.*

const val MAX_H = 100
const val MAX_W = 100
const val MAX_V = MAX_H * MAX_W
const val NOT_MATCHED = -1
const val EMPTY = 0
const val HOLE = 1
const val WALL = 2

var H = 0
var W = 0
var V = 0

val grid = Array(MAX_H + 1) { IntArray(MAX_W + 1) { EMPTY } }

var horizontalGroupCnt = 0
var verticalGroupCnt = 0
val horizontalGroupNum = Array(MAX_H + 1) { IntArray(MAX_W + 1) { -1 } }
val verticalGroupNum = Array(MAX_H + 1) { IntArray(MAX_W + 1) { -1 } }

val adjList = Array(MAX_V + 1) { mutableListOf<Int>() }
val isChecked = BooleanArray(MAX_V + 1) { false }
val isMatchedTo = IntArray(MAX_V + 1) { NOT_MATCHED }

fun main(): Unit = with(System.`in`.bufferedReader()) {
    // 체스판 크기 입력
    var st = StringTokenizer(readLine()!!, " ")
    H = Integer.parseInt(st.nextToken())
    W = Integer.parseInt(st.nextToken())

    // 정점 수
    V = H * W

    for (y in 0 until H) {
        st = StringTokenizer(readLine()!!, " ")
        for (x in 0 until W) {
            grid[y][x] = st.nextToken().toInt()
        }
    }

    // 가로로 공격 가능한 것들끼리 그룹으로 묶음
    for (y in 0 until H) {
        val listX = mutableListOf<Int>()
        for (x in 0 until W + 1) {
            if (x < W && grid[y][x] != WALL) {
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
    for (x in 0 until W) {
        val listY = mutableListOf<Int>()
        for (y in 0 until H + 1) {
            if (y < H && grid[y][x] != WALL) {
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

    // EMPTY 인 칸마다 가로 그룹 번호 -> 세로 그룹 번호로 간선 생성
    for (y in 0 until H) {
        for (x in 0 until W) {
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