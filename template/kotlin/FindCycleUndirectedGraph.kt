import java.util.*
import kotlin.math.min

const val MAX_V = 3000

var V = 0
val adjList = Array(MAX_V + 1) { mutableListOf<Int>() }
val discovered = IntArray(MAX_V + 1) { -1 }
var isAssigned = BooleanArray(MAX_V + 1) { false }
val isInCycle = BooleanArray(MAX_V + 1) { false }
val stack = Stack<Int>()
var visitCnt = 0

/** 타잔 알고리즘으로 scc 를 구한다  */
fun tarjan() {
    Arrays.fill(discovered, -1)
    Arrays.fill(isAssigned, false)
    for (v in 1 .. V) {
        if (discovered[v] == -1) {
            dfs(v, -1)
        }
    }
}

/** now 이하 서브트리로부터 올라갈 수 있는 정점의 최소 발견 순서를 리턴하면서 scc 를 찾는다  */
fun dfs(cur: Int, prev: Int): Int {
    discovered[cur] = ++visitCnt
    var ret = discovered[cur]

    stack.push(cur)
    for (next in adjList[cur]) {
        if (next == prev) {
            continue
        }

        // 트리 간선인 경우
        if (discovered[next] === -1) {
            ret = min(ret, dfs(next, cur))
        }

        // 아직 scc로 묶이지 않은 정점으로 가는 교차 간선인 경우
        else if (!isAssigned[next]) {
            ret = min(ret, discovered[next])
        }

    }

    // scc 를 찾은 경우
    if (ret == discovered[cur]) {
        // cur 을 만날 때까지 stack 에서 빼면서 하나의 scc로 묶는다
        val scc = mutableListOf<Int>()
        while (!stack.isEmpty()) {
            val top = stack.pop()
            scc.add(top)
            isAssigned[top] = true
            if (top == cur) {
                break
            }
        }

	// 크기 1 초과 scc 인 경우 cycle 이다
        if (scc.size > 1) {
            for (v in scc) {
                isInCycle[v] = true
            }
        }
    }

    return ret
}