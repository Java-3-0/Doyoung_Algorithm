import java.util.*

const val INF = 987654321
var N = 0

/** LIS 의 길이를 구해서 리턴 */
fun getLengthOfLIS(seq: IntArray): Int {
    // minLast 초기화 (minLast[i]는 길이가 i인 LIS 에서 맨 뒤에 올 수 있는 가장 작은 수)
    var minLast = IntArray(N + 1)
    Arrays.fill(minLast, INF)

    // minLast 배열 계산
    for (i in 0 until N) {
        val num= seq[i]
        val idx = binSearch(minLast, num)
        minLast[idx] = num
    }

    // LIS 길이 계산
    var lenLIS = 1
    for (i in N - 1 downTo 0) {
        if (minLast[i] != INF) {
            lenLIS = i + 1
            break
        }
    }

    return lenLIS
}

/** 오름차순으로 정렬되어 있는 seq 에서, num 이상의 수 중 가장 왼쪽 것의 인덱스를 리턴 */
fun binSearch(seq: IntArray, num: Int): Int {
    var left = 0
    var right = seq.size - 1
    while (left < right) {
        val mid = (left + right) / 2
        if (seq[mid] < num) {
            left = mid + 1
        } else {
            right = mid
        }
    }

    return left
}