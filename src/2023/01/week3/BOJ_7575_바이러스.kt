val otherLists = mutableListOf<List<Int>>()
val revOtherLists = mutableListOf<List<Int>>()

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val (n, k) = readLine()!!.trim().split(" ").map { it.toInt() }

    val firstLen = readLine()!!.toInt()
    val firstList = readLine()!!.trim().split(" ").map { it.toInt() }

    repeat(n - 1) {
        val len = readLine()!!.toInt()
        val list = readLine()!!.trim().split(" ").map { it.toInt() }
        otherLists.add(list)
        revOtherLists.add(list.reversed())
    }

    var foundVirus = false
    for (i in 0 .. firstLen - k) {
        val sub = firstList.subList(i, i + k)
        if (isVirus(sub)) {
            foundVirus = true
            break
        }
    }

    println(if (foundVirus) "YES" else "NO")
}

fun isVirus(pattern: List<Int>): Boolean {
    for (i in otherLists.indices) {
        if (!searchByKmp(otherLists[i], pattern) && !searchByKmp(revOtherLists[i], pattern)) {
            return false
        }
    }

    return true
}


fun searchByKmp(haystack: List<Int>, pattern: List<Int>): Boolean {
    // 텍스트와 패턴의 길이
    val lenT = haystack.size
    val lenP = pattern.size

    // p[i]: pattern[0]부터 pattern[i]까지의 문자열에서 접두사 = 접미사가 되는 부분의 최대 길이
    val p = getPrefixTable(pattern)
    var i = 0
    var j = 0
    while (i < lenT) {
        // j가 0이 되기 전까지, 문자열이 일치하지 않으면 j 갱신
        while (j > 0 && haystack[i] != pattern[j]) {
            j = p[j - 1]
        }
        if (haystack[i] == pattern[j]) {
            if (j == lenP - 1) {
                j = p[j]
                return true
            } else {
                j++
            }
        }
        i++
    }

    return false
}

fun getPrefixTable(pattern: List<Int>): IntArray {
    // p[i] : pattern[0]부터 pattern[i]까지의 문자열에서 접두사 = 접미사가 되는 부분의 최대 길이
    val lenP = pattern.size
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