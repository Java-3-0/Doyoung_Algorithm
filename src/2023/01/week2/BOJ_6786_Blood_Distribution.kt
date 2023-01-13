import java.util.*
import kotlin.math.min

const val O_NEG = 0
const val O_POS = 1
const val A_NEG = 2
const val A_POS = 3
const val B_NEG = 4
const val B_POS = 5
const val AB_NEG = 6
const val AB_POS = 7

const val BLOOD_TYPES = 8
const val VERTICES = BLOOD_TYPES * 2 + 2

const val INF = 987654321
const val NOT_VISITED = -1

val canGiveBlood = Array(BLOOD_TYPES) { BooleanArray(BLOOD_TYPES) { false } }

val adjList = Array(VERTICES + 1) { mutableListOf<Int>() }
val capacities = Array(VERTICES + 1) { IntArray(VERTICES + 1) { 0 } }
val flows = Array(VERTICES + 1) { IntArray(VERTICES + 1) { 0 } }
val cameFrom = IntArray(VERTICES + 1)

fun main(): Unit = with(System.`in`.bufferedReader()) {
    findCanGiveBloodRelationships()

    val bloodList = readLine()!!.trim().split(" ").map { it.toInt() }
    val patientList = readLine()!!.trim().split(" ").map { it.toInt() }

    val source = BLOOD_TYPES * 2
    val sink = BLOOD_TYPES * 2 + 1

    for (x in 0 until BLOOD_TYPES) {
        for (y in 0 until BLOOD_TYPES) {
            if (canGiveBlood[x][y]) {
                adjList[x].add(y + BLOOD_TYPES)
                adjList[y + BLOOD_TYPES].add(x)
                capacities[x][y + BLOOD_TYPES] = INF
            }
        }
    }

    for (x in 0 until BLOOD_TYPES) {
        adjList[source].add(x)
        adjList[x].add(source)
        capacities[source][x] = bloodList[x]
    }

    for (y in 0 until BLOOD_TYPES) {
        adjList[y + BLOOD_TYPES].add(sink)
        adjList[sink].add(y + BLOOD_TYPES)
        capacities[y + BLOOD_TYPES][sink] = patientList[y]
    }

    val maxFlow = getMaxFlow(source, sink)

    println(maxFlow)
}

fun getMaxFlow(source: Int, sink: Int): Int {
    var maxFlow = 0

    while (true) {
        // 메모리 초기화
        Arrays.fill(cameFrom, NOT_VISITED)

        // BFS 초기 설정
        val q: Queue<Int> = LinkedList()
        cameFrom[source] = source
        q.offer(source)

        // BFS 수행
        while (!q.isEmpty()) {
            // 큐에서 하나를 뽑아온다
            val now = q.poll()

            // 인접해 있는 정점들 탐색
            for (next in adjList.get(now)) {
                // 아직 방문하지 않았고, 유량을 보낼 수 있는 정점인 경우
                if (cameFrom[next] == NOT_VISITED && flows[now][next] < capacities[now][next]) {
                    // 경로 갱신
                    cameFrom[next] = now
                    q.offer(next)
                }
            } // end for (int next : adjList[now])
        } // end while (!q.isEmpty())

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
    } // end while (true)


    return maxFlow
}

fun findCanGiveBloodRelationships() {
    for (receiver in 0 until 8) {
        when (receiver) {
            O_NEG -> {
                canGiveBlood[O_NEG][receiver] = true
            }

            O_POS -> {
                canGiveBlood[O_NEG][receiver] = true
                canGiveBlood[O_POS][receiver] = true
            }

            A_NEG -> {
                canGiveBlood[O_NEG][receiver] = true
                canGiveBlood[A_NEG][receiver] = true
            }

            A_POS -> {
                canGiveBlood[O_NEG][receiver] = true
                canGiveBlood[O_POS][receiver] = true
                canGiveBlood[A_NEG][receiver] = true
                canGiveBlood[A_POS][receiver] = true
            }

            B_NEG -> {
                canGiveBlood[O_NEG][receiver] = true
                canGiveBlood[B_NEG][receiver] = true
            }

            B_POS -> {
                canGiveBlood[O_NEG][receiver] = true
                canGiveBlood[O_POS][receiver] = true
                canGiveBlood[B_NEG][receiver] = true
                canGiveBlood[B_POS][receiver] = true
            }

            AB_NEG -> {
                canGiveBlood[O_NEG][receiver] = true
                canGiveBlood[A_NEG][receiver] = true
                canGiveBlood[B_NEG][receiver] = true
                canGiveBlood[AB_NEG][receiver] = true
            }

            AB_POS -> {
                canGiveBlood[O_NEG][receiver] = true
                canGiveBlood[O_POS][receiver] = true
                canGiveBlood[A_NEG][receiver] = true
                canGiveBlood[A_POS][receiver] = true
                canGiveBlood[B_NEG][receiver] = true
                canGiveBlood[B_POS][receiver] = true
                canGiveBlood[AB_NEG][receiver] = true
                canGiveBlood[AB_POS][receiver] = true
            }
        }

    }
}