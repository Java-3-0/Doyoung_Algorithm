fun main(): Unit = with(System.`in`.bufferedReader()) {
    val (n, k) = readLine()!!.trim().split(" ").map { it.toInt() }
    val str = readLine()!!

    if (k >= n) {
        println(n)
    } else {
        val zArray = getZArray(str)
        val len = str.length
        var answer = 0
        for (i in n - 1 downTo 1) {
            // 접두사 = 접미사가 일치하면, 반복할 부분의 길이는 i
            if (zArray[i] == n - i) {
                // 추가해야 되는 부분의 길이 계산
                val needToAppend = (i - (len % i)) % i

                // 추가 가능하면 정답 갱신
                if (needToAppend <= k) {
                    answer = i
                    break
                }
            }
        }

        println(answer)
    }


}

/** z배열 (i 위치에서 시작되는 접미사와 전체 문자열의 longest common prefix 의 길이를 담은 배열)을 계산해서 리턴 */
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