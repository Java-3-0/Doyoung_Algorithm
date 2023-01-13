import java.util.*
import kotlin.math.min

const val MAX_V = 100
const val INF = 987654321
const val NOT_VISITED = -1

val adjList = Array(MAX_V + 1) { mutableListOf<Int>() }
val capacities = Array(MAX_V + 1) { IntArray(MAX_V + 1) { 0 } }
val flows = Array(MAX_V + 1) { IntArray(MAX_V + 1) { 0 } }
val cameFrom = IntArray(MAX_V + 1)

/** source 에서 sink 로 보낼 수 있는 최대 유량을 구한다 */
fun getMaxFlow(source: Int, sink: Int): Int {
    var maxFlow = 0

    while (true) {
        // 메모리 초기화
        Arrays.fill(cameFrom, NOT_VISITED)

        // BFS 초기 설정
        val q: Queue<Int> = LinkedList()
        cameFrom[source] = source
        q.offer(source)

        // BFS 수행해서 유량을 보낼 수 있는 경로 찾기
        while (!q.isEmpty()) {
            val now = q.poll()

            for (next in adjList[now]) {
                if (cameFrom[next] == NOT_VISITED && flows[now][next] < capacities[now][next]) {
                    cameFrom[next] = now
                    q.offer(next)
                }
            }
        }

        // 도착점까지 가지 못한 경우 무한루프 종료
        if (cameFrom[sink] == NOT_VISITED) {
            break
        }

        // 도착점까지 간 경우 최대 유량 계산
        var flow = INF
        var cur = sink
        while (cur != source) {
            val prev = cameFrom[cur]
            flow = min(flow, capacities[prev][cur] - flows[prev][cur])
            cur = prev
        }

        // 최대 유량만큼을 흘려 보낸다
        cur = sink
        while (cur != source) {
            val prev = cameFrom[cur]
            flows[prev][cur] += flow
            flows[cur][prev] -= flow
            cur = prev
        }
        maxFlow += flow
    }

    return maxFlow
}
