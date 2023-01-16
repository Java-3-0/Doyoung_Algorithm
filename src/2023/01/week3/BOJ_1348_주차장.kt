import java.util.*
import kotlin.math.min

const val MAX_H = 50
const val MAX_W = 50
const val MAX_CARS = 100
const val MAX_SPOTS = 100
const val MAX_V = MAX_CARS + MAX_SPOTS + 10
const val NOT_VISITED = -1
const val INF = 98765432109876543L
const val EMPTY = 0
const val WALL = 9999

val dy = intArrayOf(-1, 0, 1, 0)
val dx = intArrayOf(0, 1, 0, -1)

val grid = Array(MAX_H + 1) { IntArray(MAX_W + 1) { 0 } }
val visitCheck = Array(MAX_H) { BooleanArray(MAX_W) { false } }

val adjList = Array(MAX_V + 1) { mutableListOf<Int>() }
val costs = Array(MAX_V + 1) { LongArray(MAX_V + 1) { 0L } }
val capacities = Array(MAX_V + 1) { LongArray(MAX_V + 1) { 0L } }
val flows = Array(MAX_V + 1) { LongArray(MAX_V + 1) { 0L } }
val cameFrom = IntArray(MAX_V + 1) { NOT_VISITED }

var H = 0
var W = 0
var source = MAX_CARS + MAX_SPOTS + 2
var sink = MAX_CARS + MAX_SPOTS + 3

data class Position(val y: Int, val x: Int) {
    fun isVisited(): Boolean {
        return visitCheck[y][x]
    }

    fun visit() {
        visitCheck[y][x] = true
    }

    fun isInRange(): Boolean {
        return y in 0 until H && x in 0 until W
    }

    fun getNextPosition(dir: Int): Position {
        return Position(y + dy[dir], x + dx[dir])
    }

    fun getGridVal(): Int {
        return grid[y][x]
    }

    fun setGridVal(v: Int) {
        grid[y][x] = v
    }
}

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val st = StringTokenizer(readLine()!!.trim(), " ")
    H = st.nextToken().toInt()
    W = st.nextToken().toInt()

    // 차는 [0, 100), 주차장은 [100, 200) 사이 수로 나타냄
    var carNum = 0
    var spotNum = MAX_CARS
    for (y in 0 until H) {
        val line = readLine()!!
        for (x in 0 until W) {
            val c = line[x]
            when (c) {
                'C' -> {
                    grid[y][x] = ++carNum
                }

                'P' -> {
                    grid[y][x] = ++spotNum
                }

                'X' -> {
                    grid[y][x] = WALL
                }

                '.' -> {
                    grid[y][x] = EMPTY
                }
            }
        }
    }

    // 간선 연결
    for (y in 0 until H) {
        for (x in 0 until W) {
            val num = grid[y][x]
            if (isCar(num)) {
                connectEdge(source, num, 1L, 0L)
                bfs(Position(y, x))
            } else if (isSpot(num)) {
                connectEdge(num, sink, 1L, 0L)
            }
        }
    }

    // 이분탐색
    var left = 0L
    var right = (MAX_H * MAX_W + 1).toLong()
    if (!isPossible(right, carNum)) {
        println(-1)
    } else {
        while (left < right) {
            val mid = (left + right) / 2L
            if (isPossible(mid, carNum)) {
                right = mid
            } else {
                left = mid + 1
            }
        }

        println(right)
    }
}

/** costLimit 이하의 비용을 가진 간선들만으로 cars 개의 차를 모두 주차장에 매칭할 수 있는지 여부를 리턴 */
fun isPossible(costLimit: Long, cars: Int): Boolean {
    Arrays.fill(cameFrom, NOT_VISITED)
    for (arr in flows) {
        Arrays.fill(arr, 0L)
    }

    val maxFlow = getMaxFlow(costLimit)
    return maxFlow == cars.toLong()
}

/** bfs 를 돌며 차와 주차장 사이 거리들을 구해서 간선으로 연결 */
fun bfs(start: Position) {
    for (arr in visitCheck) {
        Arrays.fill(arr, false)
    }

    val q = LinkedList<Position>()
    start.visit()
    q.offer(start)
    val carNum = start.getGridVal()

    // 차와 주차장 사이 간선 연결
    var depth = 0
    while (q.isNotEmpty()) {
        val size = q.size
        repeat(size) {
            val cur = q.poll()!!
            if (isSpot(cur.getGridVal())) {
                connectEdge(carNum, cur.getGridVal(), 1L, depth.toLong())
            }

            for (dir in dy.indices) {
                val next = cur.getNextPosition(dir)
                if (next.isInRange() && !next.isVisited() && next.getGridVal() != WALL) {
                    next.visit()
                    q.offer(next)
                }
            }
        }

        depth++
    }
}

fun isCar(num: Int): Boolean {
    return num in 1 .. MAX_CARS
}

fun isSpot(num: Int): Boolean {
    return num in MAX_CARS + 1 .. MAX_CARS + MAX_SPOTS
}

/** 간선 연결 */
fun connectEdge(from: Int, to: Int, capacity: Long, cost: Long) {
    capacities[from][to] += capacity
    costs[from][to] += cost
    costs[to][from] -= cost
    adjList[from].add(to)
    adjList[to].add(from)
}

/** source 에서 sink 로 보낼 수 있는 최대 유량을 구한다 */
fun getMaxFlow(costLimit: Long): Long {
    var maxFlow = 0L

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
                if (cameFrom[next] == NOT_VISITED && flows[now][next] < capacities[now][next] && costs[now][next] <= costLimit) {
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