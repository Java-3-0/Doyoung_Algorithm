fun main() = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    val (K, N) = readLine().split(" ").map { it.toInt() }
    val list = mutableListOf<Int>()

    // 모든 수 1번씩 사용해야 하니까 리스트에 넣기
    repeat(K) {
        list.add(readLine().toInt())
    }

    // 남은 횟수는 가장 큰 수를 사용할 것이므로 리스트에 넣기
    val maxVal = list.max()
    repeat(N - K) {
        list.add(maxVal)
    }

    // 사전순 정렬
    val sortedList = list.sortedWith { a, b ->
        val strA = a.toString()
        val strB = b.toString()

        val strAB = strA + strB
        val strBA = strB + strA

        compareValues(strAB, strBA)
    }

    // 사전 역순으로 사용
    for (i in N - 1 downTo 0) {
        sb.append("${sortedList[i]}")
    }

    println(sb)
}