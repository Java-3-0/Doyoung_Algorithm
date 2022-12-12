// N = 1이면
// 5면 보이는 주사위 1개

// N = 2이면
// 3면 보이는 주사위 4개
// 2면 보이는 주사위 4개

// N >= 3이면
// 3면 보이는 주사위 4개
// 2면 보이는 주사위 4L * (N - 1L) + 4L * (N - 2L)개
// 1면 보이는 주사위 (N - 2L) * (N - 2L) * 5L + 4L * (N - 2L)개

import java.util.*

fun main() = with(System.`in`.bufferedReader()) {
    // 입력
    val N = readLine().toLong()
    val dice = readLine().split(" ").map { it.toLong() }

    // 1, 2, 3면의 최소 합 구하기
    val minimums = Array<Long>(3) { 0L }
    for (i in 0..2) {
        minimums[i] = dice[i].coerceAtMost(dice[5 - i])
    }
    Arrays.sort(minimums)

    val sumOfThree = minimums[0] + minimums[1] + minimums[2]
    val sumOfTwo = minimums[0] + minimums[1]
    val sumOfOne = minimums[0]

    // 5면의 최소 합 구하기
    var maxOfAll = 0L
    var sumOfAll = 0L
    for (num in dice) {
        maxOfAll = maxOfAll.coerceAtLeast(num);
        sumOfAll += num
    }
    val sumOfFive = sumOfAll - maxOfAll;

    // 정답 계산 및 출력
    when (N) {
        1L -> {
            println(sumOfFive)
        }

        2L -> {
            println(4L * sumOfThree + 4 * sumOfTwo)
        }

        else -> {
            val threes = 4L;
            val twos = 4L * (N - 1L) + 4L * (N - 2L)
            val ones = (N - 2L) * (N - 2L) * 5L + 4L * (N - 2L)
            println(threes * sumOfThree + twos * sumOfTwo + ones * sumOfOne)
        }
    }

}


