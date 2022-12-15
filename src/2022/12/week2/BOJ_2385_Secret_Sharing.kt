fun main() = with(System.`in`.bufferedReader()) {
    // 입력
    val N = readLine().toInt()
    val list = readLine().split(" ")

    // 0으로 시작하는 것과 아닌 것으로 분리
    val zeroList = mutableListOf<String>()
    val nonZeroList = mutableListOf<String>()
    for (str in list) {
        if (str[0] == '0') {
            zeroList.add(str)
        } else {
            nonZeroList.add(str)
        }
    }

    // 0으로 시작하는 것만 있는 경우
    if (nonZeroList.isEmpty()) {
        println("INVALID")
    }

    // 0으로 시작하지 않는 것이 존재하는 경우
    else {
        // 사전순 정렬
        val sortedZeroList = zeroList.sortedWith { a, b ->
            compareValues(a + b, b + a)
        }
        val sortedNonZeroList = nonZeroList.sortedWith { a, b ->
            compareValues(a + b, b + a)
        }.toMutableList()

        val sb = StringBuilder()
        // 0으로 시작하는 것이 없으면 그냥 전부 출력하면 됨
        if (zeroList.isEmpty()) {
            for (nonZero in sortedNonZeroList) {
                sb.append(nonZero)
            }
        }

        // 0으로 시작하는 것이 있으면 0이 아닌 하나 출력 -> 0으로 시작하는 것 모두 출력 -> 0이 아닌 나머지 출력
        else {
            val sbZero = StringBuilder()
            // 0으로 시작하는 것 모두 sbZero에 담기
            for (zero in sortedZeroList) {
                sbZero.append(zero)
            }

            // 최소인 한 개 찾아서 사용하고 sortedNonZeroList에서 제거
            val min = findMin(sortedNonZeroList, sbZero.toString())
            sortedNonZeroList.remove(min)
            sb.append(min)

            // 0으로 시작하는 것 사용
            sb.append(sbZero.toString())

            // 0으로 시작하지 않는 것 아까 쓴 하나 빼고 나머지 모두 사용
            for (nonZero in sortedNonZeroList) {
                sb.append(nonZero)
            }

        }

        println(sb)
    }



}

// 문자열 리스트에서 0이 아닌 것 중
fun findMin(list: List<String>, zeroStr: String): String {
    var min = "9999999"
    for (s in list) {
        if (s[0] == '0') {
            continue
        }

        if (s + zeroStr + min < min + zeroStr + s) {
            min = s
        }
    }

    return min
}