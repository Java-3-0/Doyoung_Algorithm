val matchedIdxSet = HashSet<Int>()

fun main() = with(System.`in`.bufferedReader()) {
    val N = readLine().toInt()

    val arrA = readLine().split(" ").map { it.toInt() }.toIntArray()
    val arrB = readLine().split(" ").map { it.toInt() }.toIntArray()

    val answer = solve(N, arrA, arrB)

    println(answer)
}

fun solve(N: Int, arrA: IntArray, arrB: IntArray): Int {
    var cnt = 0

    val mapNumToIdx = HashMap<Int, Int>()
    for (i in arrA.indices) {
        mapNumToIdx[arrA[i]] = i
    }

    val sortedArr = arrA.copyOf(arrA.size)
    sortedArr.sort()

    // 초기 상태
    for (i in arrA.indices) {
        if (arrA[i] == arrB[i]) {
            matchedIdxSet.add(i)
        }
    }
    if (matchedIdxSet.size == N) {
        return 1
    }

    // 정렬 시도
    for (last in N - 1 downTo 1) {
        if (arrA[last] != sortedArr[last]) {
            cnt++

            // 필요한 원소가 어디 있는지 찾기
            val pos = mapNumToIdx[sortedArr[last]]!!

            // pos와 last를 swap하기
            val tmp = arrA[pos]
            arrA[pos] = arrA[last]
            arrA[last] = tmp

            // map에도 반영
            mapNumToIdx[arrA[pos]] = pos
            mapNumToIdx[arrA[last]] = last

            // swap 한 후 arrB와의 일치 여부 갱신
            if (arrA[pos] == arrB[pos]) {
                matchedIdxSet.add(pos)
            } else {
                matchedIdxSet.remove(pos)
            }

            if (arrA[last] == arrB[last]) {
                matchedIdxSet.add(last)
            } else {
                matchedIdxSet.remove(last)
            }

            // 성공하면 리턴
            if (matchedIdxSet.size == N) {
                return 1
            }

        }
    }

    return 0
}