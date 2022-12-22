import java.util.*
import kotlin.math.min

const val MAX_LEN = 50
const val CACHE_EMPTY = -1
const val INF = 987654321

var text = charArrayOf()
val cache = Array(MAX_LEN + 1) { Array(MAX_LEN + 1) { CACHE_EMPTY } }


fun main() = with(System.`in`.bufferedReader()) {
    text = readLine()!!.toCharArray()

    // swap 을 안 쓴 경우
    var answer = getMinOperations(0, text.size - 1)

    // swap 을 한 번 쓴 경우
    for (i in text.indices) {
        for (j in i + 1 until text.size) {
            if (text[i] != text[j]) {
                swap(i, j)

                for (k in cache.indices) {
                    Arrays.fill(cache[k], CACHE_EMPTY)
                }

                val ops = 1 + getMinOperations(0, text.size - 1)
                answer = min(answer, ops)

                swap(i, j)
            }
        }
    }

    println(answer)
}

fun getMinOperations(left: Int, right: Int): Int {
    if (right <= left) {
        return 0
    }

    if (cache[left][right] != CACHE_EMPTY) {
        return cache[left][right]
    }

    var ret = INF

    // 왼쪽 추가 or 오른쪽 삭제
    ret = min(ret, 1 + getMinOperations(left, right - 1))

    // 왼쪽 삭제 or 오른쪽 추가
    ret = min(ret, 1 + getMinOperations(left + 1, right))

    // 왼쪽 or 오른쪽 문자 치환
    ret = min(ret, (if (text[left] == text[right]) 0 else 1) + getMinOperations(left + 1, right - 1))

    cache[left][right] = ret
    return ret
}

fun swap(idx1: Int, idx2: Int) {
    val tmp = text[idx1]
    text[idx1] = text[idx2]
    text[idx2] = tmp
}