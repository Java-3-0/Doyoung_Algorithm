import java.util.*

const val MAX_NUM = 1000
const val INF = 987654321

data class Item(val num: Int, var cnt: Int) : Comparable<Item> {
    override fun compareTo(other: Item): Int {
        return this.num.compareTo(other.num)
    }
}

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val N = readLine()!!.toInt()

    val numList = readLine()!!.split(" ").map { it.toInt() }
    val counts = IntArray(MAX_NUM + 1) { 0 }

    for (num in numList) {
        counts[num]++
    }

    val itemPQ = PriorityQueue<Item>()
    for (num in 0 .. MAX_NUM) {
        if (counts[num] > 0) {
            itemPQ.offer(Item(num, counts[num]))
        }
    }

    val answerList = mutableListOf<Int>()

    // 3개 이상일 때는 작은 것부터 최대한 뺀다
    var prev = -INF
    while (itemPQ.size >= 3) {
        val item1 = itemPQ.poll()
        // 연속이면 그 다음걸 빼서 1개만 쓰자
        if (prev + 1 == item1.num) {
            val item2 = itemPQ.poll()
            item2.cnt--
            answerList.add(item2.num)
            prev = item2.num

            if (item2.cnt > 0) {
                itemPQ.offer(item2)
            }
            itemPQ.offer(item1)
        }
        // 연속이 아니면 그대로 처리
        else {
            repeat(item1.cnt) {
                answerList.add(item1.num)
            }
            prev = item1.num
        }
    }

    // 2개 남은 경우 연속이 안 생기는지 보고 처리
    if (itemPQ.size == 2) {
        val item1 = itemPQ.poll()
        val item2 = itemPQ.poll()

        // 이전 것과 연속이 되는 경우 연속이 아닌 것을 1개만 쓴다
        if (answerList.isNotEmpty() && answerList.last() + 1 == item1.num) {
            item2.cnt--
            answerList.add(item2.num)
        } else if (answerList.isNotEmpty() && answerList.last() + 1 == item1.num) {
            item1.cnt--
            answerList.add(item1.num)
        }

        // 연속이 되지 않는 순서로 남은 걸 다 쓴다
        if (item1.num + 1 == item2.num) {
            repeat(item2.cnt) {
                answerList.add(item2.num)
            }
            repeat(item1.cnt) {
                answerList.add(item1.num)
            }
        } else {
            repeat(item1.cnt) {
                answerList.add(item1.num)
            }
            repeat(item2.cnt) {
                answerList.add(item2.num)
            }
        }
    }

    // 처음부터 0개나 1개였으면 그냥 빼고 끝내자
    else {
        while (itemPQ.isNotEmpty()) {
            val item = itemPQ.poll()
            repeat(item.cnt) {
                answerList.add(item.num)
            }
        }
    }

    // 출력
    val sb = StringBuilder()
    for (answer in answerList) {
        sb.append("$answer ")
    }
    sb.append("\n")

    print(sb)

}