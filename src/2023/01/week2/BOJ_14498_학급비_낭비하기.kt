import java.util.*

const val MAX_N = 128
const val MAX_M = 128
const val MAX_V = 512
const val NOT_MATCHED = -1

var N = 0
var M = 0
var V = 0

val adjList = Array(MAX_V + 1) { mutableListOf<Int>() }
val isChecked = BooleanArray(MAX_V + 1) { false }
val isMatchedTo = IntArray(MAX_V + 1) { NOT_MATCHED }
val opinionList = mutableListOf<Opinion>()

data class Opinion(val like: Int, val dislike: Int)

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val st = StringTokenizer(readLine()!!, " ")
    N = st.nextToken().toInt()
    M = st.nextToken().toInt()
    V = st.nextToken().toInt()

    // 의견 입력
    for (i in 0 until V) {
        var (n, m, type) = readLine()!!.split(" ").map { it.toInt() }
        when (type) {
            0 -> {
                opinionList.add(Opinion(n, m + MAX_N))
            }

            1 -> {
                opinionList.add(Opinion(m + MAX_N, n))
            }
        }
    }

    // 의견 충돌이 생기는 쌍마다 간선 생성 (n을 좋아하는 사람 -> m을 좋아하는 사람 방향으로)
    for (i in 0 until V) {
        for (j in i + 1 until V) {
            val opinion1 = opinionList[i]
            val opinion2 = opinionList[j]

            if (opinion1.like == opinion2.dislike || opinion1.dislike == opinion2.like) {
                if (opinion1.like <= MAX_N) {
                    adjList[i].add(j)
                } else {
                    adjList[j].add(i)
                }
            }
        }
    }

    // 간선 정보 오름차순정렬
    for (list in adjList) {
        list.sort()
    }

    // 최대 매칭 수를 구한다
    val answer = getMaxMatches()
    println(answer)

}

/** 이분매칭 최대 매칭 수 리턴 */
fun getMaxMatches(): Int {
    var ret = 0

    for (x in 0 until V) {
        Arrays.fill(isChecked, false)
        if (dfs(x)) {
            ret++
        }
    }

    return ret
}

/** dfs 를 수행하면서 매칭 시도  */
fun dfs(x: Int): Boolean {
    for (y in adjList[x]) {
        if (!isChecked[y]) {
            isChecked[y] = true
            if (isMatchedTo[y] == NOT_MATCHED || dfs(isMatchedTo[y])) {
                isMatchedTo[y] = x
                return true
            }
        }
    }

    return false
}