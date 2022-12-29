import java.util.*

const val MAX_NUM = 500000
const val NOT_VISITED = -1

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val (start, end) = readLine()!!.split(" ").map { it.toInt() }

    val answer = bfs(start, end)

    println(answer)
}

fun bfs(start: Int, end: Int): Int {
    val visitTime = Array(MAX_NUM + 1) { IntArray(2) { NOT_VISITED } }
    val q = LinkedList<Int>()

    visitTime[start][0] = 0
    q.offer(start)

    // 모든 점에 대해서 짝수 시간, 홀수 시간에 도착하는 최소 시간을 visitTime 에 저장한다
    var depth = 0
    while (q.isNotEmpty()) {
        val size = q.size
        repeat(size) {
            val cur = q.poll()!!

            val nextArr = intArrayOf(cur + 1, cur - 1, cur * 2)
            for (next in nextArr) {
                if (next in 0 .. MAX_NUM && visitTime[next][(depth + 1) % 2] == NOT_VISITED) {
                    visitTime[next][(depth + 1) % 2] = depth + 1
                    q.offer(next)
                }
            }
        }

        depth++
    }

    // 동생의 모든 위치에 대해서 탐색하다가 처음 만나는 때를 리턴
    var brotherPosition = end
    var brotherTime = 0
    while (brotherPosition <= MAX_NUM) {
        val arriveTime = visitTime[brotherPosition][brotherTime % 2]
        if (arriveTime != NOT_VISITED && arriveTime <= brotherTime) {
            return brotherTime
        }

        brotherTime++
        brotherPosition += brotherTime
    }

    return -1
}
