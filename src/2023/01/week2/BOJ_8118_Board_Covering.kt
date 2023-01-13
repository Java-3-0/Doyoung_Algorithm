import java.util.*

const val MAX_DOMAIN = 2500
const val MAX_RANGE = 2500
const val NOT_MATCHED = -1

var DOMAIN = MAX_DOMAIN
var RANGE = MAX_RANGE

val adjList = Array(MAX_DOMAIN + 1) { mutableListOf<Int>() }
val isChecked = BooleanArray(MAX_RANGE + 1) { false }
val isMatchedTo = IntArray(MAX_RANGE + 1) { NOT_MATCHED }

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    val st = StringTokenizer(readLine()!!.trim(), " ")
    val n = st.nextToken().toInt()
    val a = st.nextToken().toInt() - 1
    val b = st.nextToken().toInt() - 1
    val c = st.nextToken().toInt() - 1

    DOMAIN = n * n
    RANGE = n * n
    for (idx1 in 0 until n * n) {
        if (idx1 == a || idx1 == b || idx1 == c) {
            continue
        }

        val y = idx1 / n
        val x = idx1 % n

        val nextY = y + 1
        val nextX = x + 1

        val isInDomain = (y + x) % 2 == 0
        if (nextY < n) {
            val idx2 = nextY * n + x
            if (idx2 != a && idx2 != b && idx2 != c) {
                if (isInDomain) {
                    adjList[idx1].add(idx2)
                } else {
                    adjList[idx2].add(idx1)
                }
            }
        }

        if (nextX < n) {
            val idx2 = y * n + nextX
            if (idx2 != a && idx2 != b && idx2 != c) {
                if (isInDomain) {
                    adjList[idx1].add(idx2)
                } else {
                    adjList[idx2].add(idx1)
                }
            }
        }
    }

    val maxMatches = getMaxBipartiteMatches()
    if (maxMatches * 2 + 3 == DOMAIN) {
        for (i in 0 until RANGE) {
            if (isMatchedTo[i] != NOT_MATCHED) {
                sb.append("${i + 1} ${isMatchedTo[i] + 1}\n")
            }
        }
    } else {
        sb.append("NIE\n")
    }

    print(sb)

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