import java.util.*

const val MAX_DOMAIN = 100
const val MAX_RANGE = 100 * 100
const val NOT_MATCHED = -1

var DOMAIN = MAX_DOMAIN
var RANGE = MAX_RANGE

val takenCourseSet = hashSetOf<Int>()
val adjList = Array(MAX_DOMAIN + 1) { mutableListOf<Int>() }
val isChecked = BooleanArray(MAX_RANGE + 1) { false }
val rangeToDomain = IntArray(MAX_RANGE + 1) { NOT_MATCHED }
val domainToRange = IntArray(MAX_DOMAIN + 1) { NOT_MATCHED }

/** takenCourseSet 에 있는 것일수록 앞으로, 포함 여부가 같다면 번호의 오름차순으로 정렬하기 위한 컴퍼레이터 */
object MyComparator : Comparator<Int> {
    override fun compare(n1: Int, n2: Int): Int {
        val has1 = if (takenCourseSet.contains(n1)) 0 else 1
        val has2 = if (takenCourseSet.contains(n2)) 0 else 1

        if (has1 == has2) {
            return n1.compareTo(n2)
        }
        return has1.compareTo(has2)
    }
}

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    // 이미 들은 수업들 입력
    val takenCnt = readLine()!!.trim().toInt()
    if (takenCnt > 0) {
        takenCourseSet.addAll(readLine()!!.trim().split(" ").map { it.toInt() })
    } else {
        readLine()!!
    }

    // 졸업 조건들 입력
    val n = readLine()!!.trim().toInt()
    var sumNeed = 0
    var y = 0
    for (cond in 1 .. n) {
        val st = StringTokenizer(readLine()!!.trim(), " ")
        val need = st.nextToken().toInt()
        val candidateCnt = st.nextToken().toInt()
        sumNeed += need

        // 조건을 정점 분할해서 수업 -> 조건으로의 간선 생성
        repeat(candidateCnt) {
            val course = st.nextToken().toInt()
            for (dy in 0 until need) {
                adjList[course].add(y + dy)
            }
        }

        y += candidateCnt
    }

    // 이분 매칭 계산
    val matches = getMaxBipartiteMatches()

    // 필요한 졸업 요건 수업 수만큼 매칭되면 성공
    if (sumNeed == matches) {
        // 매칭된 domain 들을 오름차순 출력
        val answerList = mutableListOf<Int>()
        for (i in domainToRange.indices) {
            if (domainToRange[i] != NOT_MATCHED && !takenCourseSet.contains(i)) {
                answerList.add(i)
            }
        }
        sb.append("${answerList.size}\n")
        for (answer in answerList) {
            sb.append("$answer ")
        }
    }

    // 아니면 실패
    else {
        sb.append("-1\n")
    }

    print(sb)
}

/** 이분매칭 최대 매칭 수 리턴 */
fun getMaxBipartiteMatches(): Int {
    var ret = 0

    // 이미 들은 수업이 매칭 우선순위를 갖도록, 들은 여부가 같을 때는 번호 오름차순으로 우선순위를 갖도록 정렬
    val domainList = mutableListOf<Int>()
    for (i in 1 .. DOMAIN) {
        domainList.add(i)
    }
    val sortedDomainList = domainList.sortedWith(MyComparator)

    for (from in sortedDomainList) {
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
            if (rangeToDomain[to] == NOT_MATCHED || dfs(rangeToDomain[to])) {
                rangeToDomain[to] = from
                domainToRange[from] = to
                return true
            }
        }
    }

    return false
}