// suffixArray[i]: 사전 순으로 i번째 오는 접미사의 시작 위치
// invSuffixArray[i]: i 위치에서 시작하는 접미사가 사전 순으로 몇 번째인지
// lcpArray[i]: 사전 순 i번째 접미사와 사전 순 (i + 1)번째 접미사의 최장 공통 접두어 길이

import kotlin.math.min

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

/** 배열의 역함수가 되는 배열를 구해서 리턴 */
fun getInverseArray(arr: List<Int>): List<Int> {
    val invArray = MutableList(arr.size) { -1 }
    for (i in arr.indices) {
        invArray[arr[i]] = i
    }
    return invArray
}

/** 문자열의 longest common prefix array 를 구해서 리턴 */
fun getLcpArray(str: String, suffixArray: List<Int>): List<Int> {
    val len = str.length
    val lcpArray = MutableList(len - 1) { -1 }

    val invSuffixArray = getInverseArray(suffixArray)

    var commonPrefixLen = 0
    for (i in 0 until len) {
        // idx 는 i 위치에서 시작하는 접미사가 사전 순으로 몇 번째인지를 나타낸다
        val idx = invSuffixArray[i]
        if (idx == len - 1) {
            continue
        }

        // 사전 순으로 다음에 오는 접미사(= j 위치에서 시작하는 접미사)와 공통 접두어 길이를 구한다
        val j = suffixArray[idx + 1]
        while (i + commonPrefixLen < len && j + commonPrefixLen < len) {
            if (str[i + commonPrefixLen] == str[j + commonPrefixLen]) {
                commonPrefixLen++
            } else {
                break
            }
        }

        // 구한 값을 lcp 배열에 저장
        lcpArray[idx] = commonPrefixLen

        /*
        i + 1 위치에서 시작하는 접미사는 i 위치에서 시작하는 접미사가 일치했던 것에서
        앞 글자 하나 뺀 것만큼은 똑같이 일치하므로
        0부터가 아니라 commonPrefixLen - 1 부터만 탐색하도록 한다
         */
        if (commonPrefixLen > 0) {
            commonPrefixLen--
        }
    }

    return lcpArray
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
