import kotlin.math.min

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    val str = readLine()!!

    val suffixArray = getSuffixArray(str)

    suffixArray.forEach { item -> sb.append("$item\n") }
    print(sb)
}

/** 문자열의 suffix array 를 구해서 리턴 */
fun getSuffixArray(str: String): List<Int> {
    val len = str.length
    val suffixArr = MutableList(len) { 0 }

    var groupNum = IntArray(len + 1) { -1 }
    for (i in 0 until len) {
        suffixArr[i] = i
        groupNum[i] = str[i].code
    }

    // 비교 길이를 2배씩 증가사키면서 비교
    var lenToCompare = 1
    while (lenToCompare < len) {
        // 그룹 번호 기준으로 원하는 길이만큼을 비교하는 컴퍼레이터 생성해서 정렬
        val myCmp = MyComparator(lenToCompare, groupNum)
        suffixArr.sortWith(myCmp)

        // 다음 그룹 번호 구하기
        val nextGroupNum = IntArray(len + 1) { -1 }
        nextGroupNum[suffixArr[0]] = 0
        for (i in 1 until len) {
            nextGroupNum[suffixArr[i]] = nextGroupNum[suffixArr[i - 1]]
            if (myCmp.compare(suffixArr[i - 1], suffixArr[i]) < 0) {
                nextGroupNum[suffixArr[i]]++
            }
        }

        // 다음 비교를 위해 갱신
        groupNum = nextGroupNum
        lenToCompare *= 2
    }

    return suffixArr
}

/** lenToCompare 길이만큼을 비교하는데, 이전에 구해 둔 groupNum 을 이용하는 컴퍼레이터 */
class MyComparator(private val lenToCompare: Int, private val groupNum: IntArray) : Comparator<Int> {
    override fun compare(idx1: Int, idx2: Int): Int {
        if (groupNum[idx1] == groupNum[idx2]) {
            val left = min(idx1 + lenToCompare, groupNum.size - 1)
            val right = min(idx2 + lenToCompare, groupNum.size - 1)
            return groupNum[left].compareTo(groupNum[right])
        }
        return groupNum[idx1].compareTo(groupNum[idx2])
    }
}