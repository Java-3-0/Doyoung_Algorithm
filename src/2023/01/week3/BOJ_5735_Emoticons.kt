import java.util.*

data class Position(val left: Int, val right: Int) : Comparable<Position> {
    // left 오름차순, right 내림차순으로 정렬
    override fun compareTo(other: Position): Int {
        if (this.left == other.left) {
            return -this.right.compareTo(other.right)
        }
        return this.left.compareTo(other.left)
    }
}

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    while (true) {
        val trie = Trie()

        val (n, m) = readLine()!!.trim().split(" ").map { it.toInt() }
        if (n == 0 && m == 0) {
            break
        }

        repeat(n) {
            val emoticon = readLine()!!
            trie.insert(emoticon)
        }

        trie.calcFailureFunctions()


        var answer = 0
        repeat(m) {
            val str = readLine()!!
            val found = trie.searchByKMP(str)
            answer += found
        }

        sb.append("$answer\n")
    }

    print(sb)

}

/** 트라이 객체 */
class Trie {
    private val root = Node()

    /** 트라이에 문자열 하나를 추가 */
    fun insert(str: String) {
        var cur = root
        for (i in str.indices) {
            val c = str[i]

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

            cur.depth = i + 1
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
        for (i in str.indices) {
            val c = str[i]
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

                // 지금 찾은 것과 겹치는 부분이 있는 이모티콘들은 같이 지워질 것이므로
                // 찾고 나면 root 로 이동해서 다음 위치부터 새로 시작되는 이모티콘을 찾는다.
                cur = root
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

    var depth = 0
}