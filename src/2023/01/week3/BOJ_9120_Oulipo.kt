fun main(): Unit = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    val tests = readLine()!!.trim().toInt()
    repeat( tests) {
        val pattern = readLine()!!
        val text = readLine()!!
        val foundPositions = searchByKmp(text, pattern)
        sb.append("${foundPositions.size}\n")
    }

    print(sb)
}



/** kmp 알고리즘으로 text 에서 pattern 을 찾은 위치들의 리스트를 리턴 */
fun searchByKmp(text: String, pattern: String): List<Int> {
    // 패턴을 찾는 데 성공한 위치들을 담을 리스트
    val ret = mutableListOf<Int>()

    // 텍스트와 패턴의 길이
    val lenT = text.length
    val lenP = pattern.length

    // p[i]: pattern[0]부터 pattern[i]까지의 문자열에서 접두사 = 접미사가 되는 부분의 최대 길이
    val p = getPrefixTable(pattern)
    var i = 0
    var j = 0
    while (i < lenT) {
        // j가 0이 되기 전까지, 문자열이 일치하지 않으면 j 갱신
        while (j > 0 && text[i] != pattern[j]) {
            j = p[j - 1]
        }
        if (text[i] == pattern[j]) {
            if (j == lenP - 1) {
                ret.add(i - j)
                j = p[j]
            } else {
                j++
            }
        }
        i++
    }

    return ret
}

/** 접두사 접미사 일치 테이블을 구해서 리턴 */
fun getPrefixTable(pattern: String): IntArray {
    // p[i] : pattern[0]부터 pattern[i]까지의 문자열에서 접두사 = 접미사가 되는 부분의 최대 길이
    val lenP = pattern.length
    val p = IntArray(lenP)

    // p[0]만 예외적으로 0으로 처리
    p[0] = 0
    var i = 1
    var j = 0
    while (i < lenP) {
        while (j > 0 && pattern[i] != pattern[j]) {
            j = p[j - 1]
        }
        if (pattern[i] == pattern[j]) {
            p[i] = ++j
        } else {
            p[i] = 0
        }
        i++
    }

    return p
}