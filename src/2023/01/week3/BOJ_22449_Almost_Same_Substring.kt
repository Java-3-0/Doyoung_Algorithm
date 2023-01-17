const val DUMMY = "*"

fun main(): Unit = with(System.`in`.bufferedReader()) {
    // 입력
    val s = readLine()!!
    val t = readLine()!!
    val lenS = s.length
    val lenT = t.length

    // t + DUMMY + s 인 문자열을 만들고 z 배열을 구한다
    val str = "$t$DUMMY$s"
    val zArray = getZArray(str)

    // (t의 역순) + DUMMY + (s의 역순)인 문자열을 만들고 z 배열을 구한다
    val revStr = "${t.reversed()}$DUMMY${s.reversed()}"
    val revZArray = getZArray(revStr)

    // 앞에서부터 일치하는 개수 + 뒤에서부터 일치하는 개수 == lenT - 1 인 경우를 센다
    var answer = 0
    for (i in 0 .. lenS - lenT) {
        val idx = lenT + DUMMY.length + i
        val revIdx = DUMMY.length + lenS- i

        if (zArray[idx] + revZArray[revIdx] == lenT - 1) {
            answer++
        }
    }

    println(answer)
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