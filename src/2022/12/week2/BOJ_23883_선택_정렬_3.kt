fun main() = with(System.`in`.bufferedReader()) {
    val (N, K) = tokenizeOneLineToIntList()
    val arr = tokenizeOneLineToIntList().toIntArray()

    solve(N, K, arr)
}

fun solve(n: Int, k: Int, arr: IntArray) {
    var cnt = 0

    val mapNumToIdx = HashMap<Int, Int>()
    for (i in arr.indices) {
        mapNumToIdx[arr[i]] = i
    }

    val sortedArr = arr.copyOf(arr.size)
    sortedArr.sort()

    for (last in n - 1 downTo 1) {
        if (arr[last] != sortedArr[last]) {
            cnt++

            // 필요한 원소가 어디 있는지 찾기
            val pos = mapNumToIdx[sortedArr[last]]!!

            // pos와 last를 swap하기
            val tmp = arr[pos]
            arr[pos] = arr[last]
            arr[last] = tmp

            // map에도 반영
            mapNumToIdx[arr[pos]] = pos
            mapNumToIdx[arr[last]] = last

            if (cnt == k) {
                println("${arr[pos]} ${arr[last]}")
            }
        }
    }

    if (cnt < k) {
        println(-1)
    }

}


fun tokenizeOneLineToIntList(): List<Int> {
    return readLine()!!.split(" ").map { it.toInt() }
}