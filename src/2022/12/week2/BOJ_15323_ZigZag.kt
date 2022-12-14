import java.util.*

const val ALPHABETS = 26

data class Choice(val word: String, var cnt: Int) : Comparable<Choice> {
    override fun compareTo(other: Choice): Int {
        return compareValuesBy(this, other, { it.cnt }, { it.word })
    }
}

fun main() = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    val (K, N) = readLine().split(" ").map { it.toInt() }

    val priorityQueues = Array<PriorityQueue<Choice>>(ALPHABETS + 1) { PriorityQueue() }
    repeat(K) {
        val word = readLine()
        val idx = word[0] - 'a'
        priorityQueues[idx].offer(Choice(word, 0))
    }

    repeat(N) {
        val idx = readLine()[0] - 'a'
        val choice = priorityQueues[idx].poll()
        sb.append("${choice.word}\n")

        choice.cnt++
        priorityQueues[idx].offer(choice)
    }

    print(sb)
}