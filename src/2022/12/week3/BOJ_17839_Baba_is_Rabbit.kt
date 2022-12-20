
import java.util.*

fun main() = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    val N = readLine().toInt()
    val map = HashMap<String, HashSet<String>>()
    repeat(N) {
        val (p, q) = readLine().split(" is ")

        val set = map.getOrDefault(p, HashSet<String>())
        set.add(q)
        map[p] = set
    }

    val visitSet = TreeSet<String>()
    val queue = LinkedList<String>()

    val start = "Baba"
    queue.offer(start) // 1번 이상 적용해야 하므로 start는 방문체크 하지 않는다

    while (!queue.isEmpty()) {
        val now = queue.poll()

        if (map.containsKey(now)) {
            for (next in map[now]!!) {
                if (!visitSet.contains(next)) {
                    visitSet.add(next)
                    queue.offer(next)
                }
            }
        }
    }

    for (visit in visitSet) {
        sb.append("$visit\n")
    }

    print(sb)
}