import java.util.*

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val (hp, wp, hm, wm) = readLine()!!.trim().split(" ").map { it.toInt() }
    val wordMap = hashMapOf<String, Int>()
    var wordNum = 0

    // 트라이 생성
    val trie = Trie()
    val pattern = mutableListOf<Int>()
    for (y in 0 until hp) {
        val line = readLine()!!
        if (!wordMap.containsKey(line)) {
            wordMap[line] = (++wordNum)
        }

        pattern.add(wordMap[line]!!)
        trie.insert(line, wordMap[line]!!)
    }

    // 실패 함수 계산
    trie.calcFailureFunctions()

    // 위치마다 찾아진 문자열 번호를 found 에 저장
    val found = Array(hm) { IntArray(wm) { 0 } }
    for (y in 0 until hm) {
        val line = readLine()!!
        val result = trie.ahoCorasick(line)
        for ((x, wordNum) in result) {
            found[y][x] = wordNum
        }
    }

    // found 를 세로로 읽어서 haystack 문자열을 만든다
    val sbHaystack = StringBuilder()
    for (x in 0 until wm) {
        for (y in 0 until hm) {
            sbHaystack.append(found[y][x])
        }
        sbHaystack.append('*')
    }
    val haystack = sbHaystack.toString()

    // pattern 을 needle 문자열로 만든다
    val sbNeedle = StringBuilder()
    for (num in pattern) {
        sbNeedle.append(num)
    }
    val needle = sbNeedle.toString()

    // kmp 돌려서 찾은 개수를 출력
    val answer = searchByKmp(haystack, needle).size
    println(answer)


}

/** 트라이 객체 */
class Trie {
    private val root = Node()

    /** 트라이에 문자열 하나를 추가 */
    fun insert(str: String, wordNum: Int) {
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

        // 삽입한 문자열의 끝까지 도달하면 terminal 을 wordNum 으로 세팅
        cur.isTerminalOfWordNum = wordNum
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

                // isTerminal 갱신
                if (next.isTerminalOfWordNum == 0) {
                    next.isTerminalOfWordNum = next.fail.isTerminalOfWordNum
                }

                // 다음 정점 큐에 넣기
                q.offer(next)
            }
        }
    }

    /** str 이라는 문자열 안에 Trie 에 있는 문자열들이 어디 있는지 찾는다 (아호-코라식) */
    fun ahoCorasick(str: String): HashMap<Int, Int> {
        var ret = hashMapOf<Int, Int>()

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
            if (cur.isTerminalOfWordNum > 0) {
                val startIdx = i - cur.depth + 1
                ret[startIdx] = cur.isTerminalOfWordNum
            }
        }

        return ret
    }
}

/** 트라이 노드 객체 */
class Node {
    /** 현 위치에서 종료되는 문자열의 인덱스들 */
    var isTerminalOfWordNum = 0

    /** 자식 노드들과의 매핑 */
    val children = HashMap<Char, Node>()

    /** kmp 실패 시 이동할 위치 */
    var fail = this

    var depth = 0
}

/** kmp 알고리즘으로 text 에서 pattern 을 찾은 위치들의 리스트를 리턴 */
fun searchByKmp(text: String, pattern: String): List<Int> {
    // 패턴을 찾는 데 성공한 위치들을 담을 리스트
    val ret = mutableListOf<Int>()

    // 텍스트와 패턴의 길이
    val lenT = text.length
    val lenP = pattern.length

    // p[i]: pattern[0]부터 pattern[i]까지의 문자열에서 접두사 = 접미사가 되는 부분의 최대 길이
    val p = getPrefixTable(pattern)
    var i = 0
    var j = 0
    while (i < lenT) {
        // j가 0이 되기 전까지, 문자열이 일치하지 않으면 j 갱신
        while (j > 0 && text[i] != pattern[j]) {
            j = p[j - 1]
        }
        if (text[i] == pattern[j]) {
            if (j == lenP - 1) {
                ret.add(i - j)
                j = p[j]
            } else {
                j++
            }
        }
        i++
    }

    return ret
}

/** 접두사 접미사 일치 테이블을 구해서 리턴 */
fun getPrefixTable(pattern: String): IntArray {
    // p[i] : pattern[0]부터 pattern[i]까지의 문자열에서 접두사 = 접미사가 되는 부분의 최대 길이
    val lenP = pattern.length
    val p = IntArray(lenP)

    // p[0]만 예외적으로 0으로 처리
    p[0] = 0
    var i = 1
    var j = 0
    while (i < lenP) {
        while (j > 0 && pattern[i] != pattern[j]) {
            j = p[j - 1]
        }
        if (pattern[i] == pattern[j]) {
            p[i] = ++j
        } else {
            p[i] = 0
        }
        i++
    }

    return p
}