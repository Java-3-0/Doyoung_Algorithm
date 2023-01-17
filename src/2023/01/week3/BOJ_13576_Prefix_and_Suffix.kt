fun main(): Unit = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    val str = readLine()!!
    val zArray = getZArray(str)

    val len = str.length

    // li 들의 리스트
    val list = mutableListOf<Int>()
    for (i in zArray.size - 1 downTo 0) {
        if (i + zArray[i] == len) {
            list.add(zArray[i])
        }
    }

    // zArray 안에 존재하는 각 수들을 카운팅
    val counts = IntArray(100000 + 1) { 0 }
    for (z in zArray) {
        counts[z]++
    }

    // li 들의 개수
    sb.append("${list.size}\n")

    // li 마다 해당되는 ci 계산
    var c = zArray.size
    var cursor = 0
    for (l in list) {
        while (cursor < l) {
            c -= counts[cursor]
            cursor++
        }
        sb.append("$l $c\n")
    }

    print(sb)
}

fun getZArray(str: String): List<Int> {
    val len = str.length
    val zArray = MutableList(len) { 0 }
    zArray[0] = len

    var left = 0
    var right = 0
    for (i in 1 until len) {
        // right 보다 큰 i의 경우 아는 정보가 없어서 처음부터 구해야 함
        if (i > right) {
            left = i
            right = i
            while (right < len && str[right - left] == str[right]) {
                right++
            }
            right--
            zArray[i] = right - left + 1
        }

        // right 이하의 i의 경우 기존 정보를 이용 가능
        else {
            // l칸 이전에 구했을 때 일치한 부분의 길이(= zArray[k])가 i에서 right 까지 남은 길이(= right - i + 1) 이하라면 그 값을 그대로 쓴다
            val k = i - left
            if (zArray[k] < right - i + 1) {
                zArray[i] = zArray[k]
            }
            // 그렇지 않다면, right 위치부터는 새로 구한다.
            else {
                left = i
                while (right < len && str[right - left] == str[right]) {
                    right++
                }
                right--
                zArray[i] = right - left + 1
            }
        }
    }

    return zArray
}