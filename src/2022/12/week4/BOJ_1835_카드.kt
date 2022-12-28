import java.util.*

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val N = readLine().toInt()

    val deque = LinkedList<Int>()
    for (idx in 0 until N) {
        deque.addLast(idx)
    }

    val answers = IntArray(N) { 0 }

    for (turn in 1 until N) {
        repeat(turn) {
            deque.addLast(deque.pollFirst()!!)
        }

        val tableCardIdx = deque.pollFirst()!!
        answers[tableCardIdx] = turn
    }

    answers[deque.pollFirst()] = N

    val sb = StringBuilder()
    for (answer in answers) {
        sb.append("$answer ")
    }
    sb.append("\n")

    print(sb)

}