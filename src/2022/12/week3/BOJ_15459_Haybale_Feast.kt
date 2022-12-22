import java.util.*
import kotlin.math.min

data class Haybale(val idx: Int, val flavor: Long, val spiciness: Long)

fun main() = with(System.`in`.bufferedReader()) {
    // haybale 개수, 목표하는 flavor 입력
    var st = StringTokenizer(readLine()!!, " ")
    val N = st.nextToken().toInt()
    val targetFlavor = st.nextToken().toLong()

    // haybale 들의 정보 입력받아서 haybaleList 에 담기
    val haybaleList = mutableListOf<Haybale>()
    for (idx in 0 until N) {
        val (flavor, spiciness) = readLine()!!.split(" ").map { it.toLong() }
        haybaleList.add(Haybale(idx, flavor, spiciness))
    }

    // spiciness 내림차순, idx 오름차순 pq
    val pq = PriorityQueue<Haybale> { h1, h2 ->
        if (h1.spiciness == h2.spiciness) {
            h1.idx.compareTo(h2.idx)
        } else {
            -h1.spiciness.compareTo(h2.spiciness)
        }
    }

    // 투 포인터
    var left = 0
    var right = 0
    var sumFlavor = haybaleList[0].flavor
    var minSpiciness = Long.MAX_VALUE
    while (left < N) {
        // 조건이 만족된 경우, 최대 spiciness를 체크하고, 왼쪽 원소 하나 제거
        if (sumFlavor >= targetFlavor) {
            while (pq.isNotEmpty() && pq.peek().idx < left) {
                pq.poll()
            }
            if (pq.isNotEmpty()) {
                minSpiciness = min(minSpiciness, pq.peek().spiciness)
            }

            sumFlavor -= haybaleList[left].flavor
            left++
        }
        // 조건이 만족되지 않은 경우 오른쪽 원소 하나 추가
        else {
            if (right < N - 1) {
                right++
                sumFlavor += haybaleList[right].flavor
                pq.offer(haybaleList[right])
            } else {
                break
            }
        }
    }

    println(minSpiciness)
}