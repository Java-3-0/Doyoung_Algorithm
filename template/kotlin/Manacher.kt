import kotlin.math.min

/** Manacher 알고리즘으로 문자열의 각 위치를 중심으로 한 팰린드롬의 최대 길이를 구해서 배열로 리턴한다  */
fun getManacherResult(str: String): List<Int> {
    // 문자열 사이에 DUMMY 를 삽입해서 나누기
    val dividedText = getDividedText(str, '|')
//    println(dividedText.contentToString())

    val len = dividedText.size

    // ret[i]는 i번 인덱스를 중심으로 한 팰린드롬의 최대 길이
    val ret = MutableList(len) { 0 }

    // p는 j < i인 j 중에서 (j + ret[j])가 최대가 되는 j의 값 (팰린드롬의 중심점)
    var p = 0

    // r는 이미 팰린드롬을 구한 구간의 오른쪽 끝 인덱스
    var r = 0

    // Manacher 알고리즘 수행
    for (i in 0 until len) {
        // ret[i]의 초기값 결정
        if (r < i) {
            ret[i] = 0
        } else {
            ret[i] = min(ret[2 * p - i], r - i)
        }

        // text[i - ret[i] - 1]와 text[i + ret[i] + 1]가 같은 동안 A[i]를 증가시킨다
        while (0 <= i - ret[i] - 1 && i + ret[i] + 1 < len && dividedText[i - ret[i] - 1] == dividedText[i + ret[i] + 1]) {
            ret[i]++
        }

        // r, p값 갱신
        if (r < i + ret[i]) {
            r = i + ret[i]
            p = i
        }
    }
    return ret
}

/** 문자열의 각각의 문자 사이마다 '|'를 삽입한 CharArray 를 리턴 */
fun getDividedText(str: String, dummy: Char): CharArray {
    val sbTmp = StringBuilder()
    str.forEach { c -> sbTmp.append("$dummy$c") }
    sbTmp.append(dummy)
    return sbTmp.toString().toCharArray()
}