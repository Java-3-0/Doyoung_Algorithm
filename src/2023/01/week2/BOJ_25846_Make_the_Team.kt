import java.util.*
import kotlin.math.max

const val MAX_DOMAIN = 1000
const val MAX_RANGE = 200
const val NOT_MATCHED = -1
const val INF = 987654321

var DOMAIN = MAX_DOMAIN
var RANGE = MAX_RANGE

val adjList = Array(MAX_DOMAIN + 1) { mutableListOf<Int>() }
val isChecked = BooleanArray(MAX_RANGE + 1) { false }
val isMatchedTo = IntArray(MAX_RANGE + 1) { NOT_MATCHED }

fun main(): Unit = with(System.`in`.bufferedReader()) {
    RANGE = readLine()!!.toInt()
    for (to in 0 until RANGE) {
        val st = StringTokenizer(readLine()!!.trim(), " ")
        val cnt = st.nextToken().toInt()
        repeat(cnt) {
            val from = st.nextToken().toInt()
            adjList[from].add(to)
        }
    }

    getMaxBipartiteMatches()

    var answer = 0
    for (y in 0 until RANGE) {
        if (isMatchedTo[y] != NOT_MATCHED) {
            answer = max(answer, isMatchedTo[y] + 1)
        }
    }

    println(answer)
}

/** 이분매칭 최대 매칭 수 리턴 */
fun getMaxBipartiteMatches(): Int {
    var ret = 0

    for (from in 1 .. DOMAIN) {
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