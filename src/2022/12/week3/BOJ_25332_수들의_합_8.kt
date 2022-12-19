fun main() = with(System.`in`.bufferedReader()) {
    val N = readLine().toInt()
    val listA = readLine().split(" ").map { it.toInt() }
    val listB = readLine().split(" ").map { it.toInt() }

    // 두 수열의 차로 list 를 만들고 index 는 1부터 쓴다
    val seq = IntArray(N + 1) { 0 }
    for (i in 0 until N) {
        seq[i + 1] = (listB[i] - listA[i])
    }

    // 누적합 계산
    var sum = 0
    val prefixSums = IntArray(N + 1) { 0 }
    for (i in 1..N) {
        sum += seq[i]
        prefixSums[i] = sum
    }

    // 각 누적합이 몇 번 등장하는지 카운트
    val countMap = HashMap<Int, Int>()
    for (psum in prefixSums) {
        countMap[psum] = 1 + countMap.getOrDefault(psum, 0)
    }

    // 정답 계산
    var answer = 0L
    for (value in countMap.values) {
        if (value >= 2) {
            answer += value.toLong() * (value - 1).toLong() / 2L
        }
    }

    println(answer)

}