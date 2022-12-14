import java.util.*
import kotlin.math.sqrt

fun main() = with(System.`in`.bufferedReader()) {
    // 소수 미리 계산
    val limit = 5000000
    val isPrime = BooleanArray(limit + 1)
    Arrays.fill(isPrime, true)

    isPrime[0] = false
    isPrime[1] = false
    for (div in 2..sqrt(limit.toDouble()).toInt()) {
        if (isPrime[div]) {
            for (num in (div * div)..limit step (div)) {
                isPrime[num] = false
            }
        }
    }
    
    // 게임 진행하며 점수 계산
    val N = readLine().toInt()

    val isUsed = BooleanArray(limit + 1)
    Arrays.fill(isUsed, false)

    val pqs = Array<PriorityQueue<Int>>(2) { PriorityQueue(Collections.reverseOrder()) }
    var scores = Array<Long>(2) { 0L }
    var player = 0
    repeat(N) {
        val calls = readLine().split(" ").map { it.toInt() }

        repeat(2) {
            val enemy = (player + 1) % 2
            val callNum = calls[player]

            // 소수가 아닌 경우
            if (!isPrime[callNum]) {
                if (pqs[enemy].size >= 3) {
                    val first = pqs[enemy].poll()
                    val second = pqs[enemy].poll()
                    val third = pqs[enemy].poll()

                    scores[enemy] += third.toLong()

                    pqs[enemy].offer(first)
                    pqs[enemy].offer(second)
                    pqs[enemy].offer(third)
                } else {
                    scores[enemy] += 1000L
                }
            }
            // 이미 나온 소수인 경우
            else if (isUsed[callNum]) {
                scores[player] -= 1000L
            }
            // 처음 나온 소수인 경우
            else {
                isUsed[callNum] = true
                pqs[player].offer(callNum)
            }

            player = enemy
        }

    }

    // 결과 출력
    if (scores[0] < scores[1]) {
        println("소수 마스터 갓규성")
    } else if (scores[0] == scores[1]) {
        println("우열을 가릴 수 없음")
    } else {
        println("소수의 신 갓대웅")
    }
}