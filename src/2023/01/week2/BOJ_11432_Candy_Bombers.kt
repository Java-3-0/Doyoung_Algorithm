import java.util.*

const val MAX_DOMAIN = 200
const val MAX_RANGE = 200
const val NOT_MATCHED = -1

var DOMAIN = MAX_DOMAIN
var RANGE = MAX_RANGE

val adjList = Array(MAX_DOMAIN + 1) { mutableListOf<Int>() }
val isChecked = BooleanArray(MAX_RANGE + 1) { false }
val isMatchedTo = IntArray(MAX_RANGE + 1) { NOT_MATCHED }

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    val tests = readLine()!!.trim().toInt()
    for (tc in 1 .. tests) {
        initMemory()

        val (m, n) = readLine()!!.trim().split(" ").map { it.toInt() }
        DOMAIN = n
        RANGE = m

        for (from in 1 .. DOMAIN) {
            val st = StringTokenizer(readLine()!!.trim(), " ")
            val cnt = st.nextToken().toInt()
            repeat(cnt) {
                val to = st.nextToken().toInt()
                adjList[from].add(to)
            }
        }

        val answer = getMaxBipartiteMatches()
        sb.append("Data Set $tc:\n")
        sb.append("$answer\n")
        sb.append("\n")
    }

    print(sb)

}

/** 메모리 초기화 */
fun initMemory() {
    for (list in adjList) {
        list.clear()
    }
    Arrays.fill(isChecked, false)
    Arrays.fill(isMatchedTo, NOT_MATCHED)
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