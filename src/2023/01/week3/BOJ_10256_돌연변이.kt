import java.util.*

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    val tests = readLine()!!.trim().toInt()
    repeat(tests) {
        // 입력
        val (n, m) = readLine()!!.trim().split(" ").map { it.toInt() }
        val dna = readLine()!!
        val marker = readLine()!!

        // marker 와 그 mutation 들의 집합 구하기
        val mutationSet = getMutationSet(marker)

        // trie 생성
        val trie = Trie()
        for (mutation in mutationSet) {
            trie.insert(mutation)
        }

        // 실패 함수 계산
        trie.calcFailureFunctions()

        val answer = trie.searchByKMP(dna)
        sb.append("$answer\n")
    }

    print(sb)
}

fun getMutationSet(marker: String): HashSet<String> {
    val ret = hashSetOf<String>()
    ret.add(marker)

    val len = marker.length

    for (a in 0 until len) {
        for (c in 0 until len - a) {
            val b = len - a - c

            val strA = marker.substring(0, a)
            val strB = marker.substring(a, a + b).reversed()
            val strC = marker.substring(a + b, a + b + c)

            ret.add("$strA$strB$strC")
        }
    }

    return ret
}

/** 트라이 객체 */
class Trie {
    private val root = Node()

    /** 트라이에 문자열 하나를 추가 */
    fun insert(str: String) {
        var cur = root
        for (c in str) {
            // 다음 노드가 있으면 찾기
            if (cur.children.containsKey(c)) {
                val next = cur.children[c]!!
                cur = next
            }
            // 다음 노드가 없으면 만들기
            else {
                val next = Node()
                cur.children[c] = next
                cur = next
            }
        }

        // 삽입한 문자열의 끝까지 도달하면 terminal 을 true 로 세팅
        cur.isTerminal = true
    }

    /** 각 Node 의 실패 함수(kmp 수행 중 일치하지 않을 때 돌아갈 위치)를 찾아서 Node 의 멤버변수로 저장 */
    fun calcFailureFunctions() {
        val q = LinkedList<Node>()
        root.fail = root
        q.offer(root)

        // bfs 탐색하면서 각 노드의 실패 함수 갱신
        while (q.isNotEmpty()) {
            val cur = q.poll()!!

            // 연결된 자식 노드들을 탐색
            for (edge in cur.children.entries) {
                val c = edge.key
                val next = edge.value

                // cur 이 루트이면 자식의 fail 함수를 루트로 연결
                if (cur == root) {
                    next.fail = root
                }

                // cur 이 루트가 아니면 자식의 fail 함수가 찾아갈 위치를 탐색
                else {
                    var dest = cur.fail

                    // fail 할 때마다 기존에 구해 둔 윗 레벨 fail 함수들을 이용해서 쭉 타고 올라간다
                    while (dest != root && !dest.children.containsKey(c)) {
                        dest = dest.fail
                    }

                    // fail 함수를 타고 올라가서 찾아낸 위치에서 c 라는 자식이 존재하는지 보고 돌아갈 위치를 갱신
                    if (dest.children.containsKey(c)) {
                        dest = dest.children[c]!!
                    }
                    next.fail = dest
                }

                // isTerminal 여부도 갱신
                next.isTerminal = next.isTerminal || next.fail.isTerminal

                // 다음 정점 큐에 넣기
                q.offer(next)
            }
        }
    }

    /** str 이라는 문자열 안에 Trie 에 있는 문자열 중 하나가 있는지 찾는다 (아호-코라식) */
    fun searchByKMP(str: String): Int {
        var ret = 0

        var cur = root
        for (c in str) {
            // 갈 수 없으면 fail 을 따라간다
            while (cur != root && !cur.children.containsKey(c)) {
                cur = cur.fail
            }

            // 갈 수 있으면 자식 노드로 이동한다
            if (cur.children.containsKey(c)) {
                cur = cur.children[c]!!
            }

            // 이 위치가 isTerminal 이면 문자열 하나를 찾은 것이다.
            if (cur.isTerminal) {
                ret++
            }
        }

        return ret
    }
}

/** 트라이 노드 객체 */
class Node {
    /** 현 위치에서 종료된 문자열의 개수 */
    var isTerminal = false

    /** 자식 노드들과의 매핑 */
    val children = HashMap<Char, Node>()

    /** kmp 실패 시 이동할 위치 */
    var fail = this
}