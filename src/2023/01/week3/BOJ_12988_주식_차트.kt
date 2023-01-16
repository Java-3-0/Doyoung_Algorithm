import java.util.*

const val MAX_N = 100
const val MAX_K = 25
const val MAX_DOMAIN = MAX_N
const val MAX_RANGE = MAX_N
const val NOT_MATCHED = -1

var DOMAIN = MAX_DOMAIN
var RANGE = MAX_RANGE

val prices = Array(MAX_N + 1) { LongArray(MAX_K + 1) { 0L } }
val adjList = Array(MAX_DOMAIN + 1) { mutableListOf<Int>() }
val isChecked = BooleanArray(MAX_RANGE + 1) { false }
val isMatchedTo = IntArray(MAX_RANGE + 1) { NOT_MATCHED }

var N = 0
var K = 0

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    val tests = readLine()!!.trim().toInt()

    // 입력
    repeat(tests) {
        initMemory()

        var st = StringTokenizer(readLine()!!.trim(), " ")
        N = st.nextToken().toInt()
        K = st.nextToken().toInt()
        for (y in 0 until N) {
            st = StringTokenizer(readLine()!!.trim(), " ")
            for (x in 0 until K) {
                prices[y][x] = st.nextToken().toLong()
            }
        }

        // 한 선이 다른 선의 아래에 있는 경우 아래 -> 위 방향으로 간선 연결
        for (y1 in 0 until N) {
            for (y2 in y1 + 1 until N) {
                if (isUnder(y1, y2)) {
                    adjList[y1].add(y2)
                } else if (isUnder(y2, y1)) {
                    adjList[y2].add(y1)
                }
            }
        }

        val maxMatches = getMaxBipartiteMatches()
        val answer = N - maxMatches
        sb.append("$answer\n")
    }

    print(sb)
}

/** y1 번 선이 y2 번 선보다 아래에 있는지 여부를 리턴 */
fun isUnder(y1: Int, y2: Int): Boolean {
    for (x in 0 until K) {
        val p1 = prices[y1][x]
        val p2 = prices[y2][x]
        if (p1 >= p2) {
            return false
        }
    }
    return true
}

/** 메모리 초기화 */
fun initMemory() {
    for (arr in prices) {
        Arrays.fill(arr, 0)
    }
    for (list in adjList) {
        list.clear()
    }

    Arrays.fill(isChecked, false)
    Arrays.fill(isMatchedTo, NOT_MATCHED)
}

/** 이분매칭 최대 매칭 수 리턴 */
fun getMaxBipartiteMatches(): Int {
    var ret = 0

    for (from in 0 until DOMAIN) {
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
