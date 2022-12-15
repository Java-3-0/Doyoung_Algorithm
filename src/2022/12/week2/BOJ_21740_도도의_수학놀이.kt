fun main() = with(System.`in`.bufferedReader()) {
    // 입력
    val N = readLine().toInt()
    val inputs = readLine().split(" ")

    // 입력받은 수들을 180도 회전시킨 수들로 문자열 리스트를 만듦
    val list = mutableListOf<String>()
    for (input in inputs) {
        list.add(rotate(input))
    }

    // 가장 긴 수 중 가장 큰 수 하나는 2번 사용
    var maxLen = 0
    var max = ""
    for (s in list) {
        val len = s.length
        if (maxLen < len) {
            maxLen = len
            max = s
        } else if (maxLen == len) {
            if (max < s) {
                max = s
            }
        }
    }
    list.add(max)

    // 사전순 정렬
    val sortedList = list.sortedWith { a, b ->
        val ab = a + b
        val ba = b + a

        compareValues(ab, ba)
    }

    // 사전 역순으로 사용해서 가장 큰 수를 만든다
    val sb = StringBuilder()
    for (i in sortedList.size - 1 downTo 0) {
        sb.append(sortedList[i])
    }
    val str = sb.toString()

    // 이 str을 180도 회전시킨다
    val answer = rotate(str)

    // 출력
    println(answer)
}

// 문자열을 180도 회전한 문자열을 리턴
fun rotate(str: String): String {
    val sb = StringBuilder()

    for (i in str.length - 1 downTo 0) {
        if (str[i] == '6') {
            sb.append('9')
        } else if (str[i] == '9') {
            sb.append('6')
        } else {
            sb.append(str[i])
        }
    }

    return sb.toString()
}