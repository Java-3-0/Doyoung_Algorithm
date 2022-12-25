import java.util.*
import kotlin.math.max

data class Present(var price: Long, val delivery: Long) : Comparable<Present> {
    fun getTotalPrice(): Long {
        return price + delivery
    }

    override fun compareTo(other: Present): Int {
        return this.getTotalPrice().compareTo(other.getTotalPrice())
    }
}

fun main() = with(System.`in`.bufferedReader()) {
    var st = StringTokenizer(readLine()!!, " ")
    val N = st.nextToken().toInt()
    val B = st.nextToken().toLong()

    val presentList = mutableListOf<Present>()
    repeat(N) {
        val (price, delivery) = readLine()!!.split(" ").map { it.toLong() }
        presentList.add(Present(price, delivery))
    }

    // 쿠폰 쓰는 모든 경우 완전탐색
    var answer = 0
    for (coupon in presentList.indices) {
        // 쿠폰을 적용한 가격으로 리스트 만들기
        val discountPresentList = mutableListOf<Present>()
        for (k in presentList.indices) {
            if (k == coupon) {
                discountPresentList.add(Present(presentList[k].price / 2, presentList[k].delivery))
            } else {
                discountPresentList.add(presentList[k])
            }
        }

        // 합산 가격 기준으로 정렬
        discountPresentList.sort()

        // 앞에서부터 최대한 사기
        var curMoney = B
        var cnt = 0
        for (present in discountPresentList) {
            val totalPrice = present.getTotalPrice()
            if (totalPrice <= curMoney) {
                curMoney -= totalPrice
                cnt++
            } else {
                break
            }
        }

        // 정답 갱신
        answer = max(answer, cnt)

    }

    println(answer)

}