import java.util.*

const val MAX_K = 20
const val MAX_N = 13
const val MAX_M = 100000

const val MAX_DOMAIN = MAX_N
const val MAX_RANGE = MAX_N
const val NOT_MATCHED = -1

var DOMAIN = MAX_DOMAIN
var RANGE = MAX_RANGE

val adjList = Array(MAX_DOMAIN + 1) { mutableListOf<Int>() }
val isChecked = BooleanArray(MAX_RANGE + 1) { false }
val isMatchedTo = IntArray(MAX_RANGE + 1) { NOT_MATCHED }

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val (N, K, M) = readLine()!!.trim().split(" ").map { it.toInt() }

    DOMAIN = N
    RANGE = N

    val diceList = mutableListOf<HashSet<Char>>()
    repeat(N) {
        val set = HashSet<Char>()
        val text = readLine()!!
        for (c in text) {
            set.add(c)
        }
        diceList.add(set)
    }

    var answer = 0
    repeat(M) {
        initMemory()

        val word = readLine()!!
        for (pos in word.indices) {
            for (diceIdx in diceList.indices) {
                if (diceList[diceIdx].contains(word[pos])) {
                    adjList[pos].add(diceIdx)
                }
            }
        }

        val maxMatches = getMaxBipartiteMatches()
        if (maxMatches == DOMAIN) {
            answer++
        }
    }

    println(answer)
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