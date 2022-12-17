fun main() = with(System.`in`.bufferedReader()) {
    val N = readLine().toInt()
    val list = readLine().split(" ").map { it.toLong() }
    val cntMap = HashMap<Long, Int>()

    for (num in list) {
        cntMap[num] = 1 + cntMap.getOrDefault(num, 0)
    }

    val goodNumSet = HashSet<Long>()

    for (i in 0 until N) {
        val num1 = list[i]
        for (j in i + 1 until N) {
            val num2 = list[j]
            val targetNum = num1 + num2

            // 찾는 수가 몇 개인지 맵에서 가져옴
            var cnt = cntMap.getOrDefault(targetNum, 0)

            // 이 개수에서 num1이랑 num2는 제외해야 함
            if (targetNum == num1) {
                cnt--
            }
            if (targetNum == num2) {
                cnt--
            }

            if (cnt > 0) {
                goodNumSet.add(targetNum)
            }
        }
    }

    var answer = 0
    for (num in list) {
        if (goodNumSet.contains(num)) {
            answer++
        }
    }

    println(answer)
}