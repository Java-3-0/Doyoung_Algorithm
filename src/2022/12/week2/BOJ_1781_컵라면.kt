import java.util.*

data class Homework(val deadLine: Long, val reward: Long);

fun main() = with(System.`in`.bufferedReader()) {
    val N = readLine().toInt()

    // deadLine 내림차순 PQ
    val allHomeworkPQ = PriorityQueue<Homework> { h1, h2 -> -compareValues(h1.deadLine, h2.deadLine) }
    repeat(N) {
        val (deadLine, reward) = readLine().split(" ").map { it.toLong() }
        allHomeworkPQ.offer(Homework(deadLine, reward))
    }

    // reward 내림차순 PQ
    val solvableHomeworkPQ = PriorityQueue<Homework> { h1, h2 -> -compareValues(h1.reward, h2.reward) }

    var totalReward = 0L
    for (date in N downTo 1) {
        while (!allHomeworkPQ.isEmpty() && allHomeworkPQ.peek().deadLine >= date) {
            solvableHomeworkPQ.offer(allHomeworkPQ.poll())
        }

        if (!solvableHomeworkPQ.isEmpty()) {
            val hwToSolve = solvableHomeworkPQ.poll()
            totalReward += hwToSolve.reward
        }
    }

    println(totalReward)
}