import java.util.*

const val MAX_N = 4000000

class DisjointSet(private val size: Int) {
    private var parent = IntArray(size) { -1 }

    /** find 연산 (대표자를 리턴) */
    fun find(a: Int): Int {
        val stack = Stack<Int>()

        // stack overflow 를 막기 위해 재귀 호출 대신 풀어서 구현
        var cur = a
        while (parent[cur] >= 0) {
            stack.push(cur)
            cur = parent[cur]
        }

        // path compression
        while (!stack.isEmpty()) {
            parent[stack.pop()] = cur
        }

        return cur
    }

    /** union 연산 (a 밑에 b를 붙인다) */
    fun union(a: Int, b: Int): Boolean {
        val aRoot = find(a)
        val bRoot = find(b)
        if (aRoot == bRoot) {
            return false
        }
        parent[aRoot] += parent[bRoot]
        parent[bRoot] = aRoot
        return true
    }
}

fun main() = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    // N, M, K 입력
    val (N, M, K) = readLine()!!.split(" ").map { it.toInt() }

    // 가진 카드들 입력받아서 myCardSet 에 넣기
    val myCardSet = HashSet<Int>()
    myCardSet.addAll(readLine()!!.split(" ").map { it.toInt() })

    // 상대가 내는 카드들 입력받아서 enemyPickList 에 넣기
    val enemyPickList = readLine()!!.split(" ").map { it.toInt() }

    // 분리 집합 생성
    val disjointSet = DisjointSet(MAX_N + 2)

    // 가진 카드들로 초기 상태 처리
    for (num in 0 until MAX_N) {
        if (!myCardSet.contains(num)) {
            disjointSet.union(num + 1, num)
        }
    }

    // 카드 하나씩 내면서 처리
    for (enemyPick in enemyPickList) {
        val myPick = disjointSet.find(enemyPick + 1)
        if (myPick <= MAX_N) {
            disjointSet.union(myPick + 1, myPick)
        }
        sb.append("$myPick\n")
    }

    print(sb)
}