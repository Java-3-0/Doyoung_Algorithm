import kotlin.math.ceil
import kotlin.math.log2

data class SegTree(val seq: List<Long>) {
    private val treeSize: Int = 1 shl (1 + ceil(log2(seq.size.toDouble()))).toInt()
    private val nodes: LongArray = LongArray(treeSize)

    init {
        initialize(0, seq.size - 1, 1)
    }

    /** 세그트리를 초기화 */
    private fun initialize(start: Int, end: Int, idx: Int) {
        if (start == end) {
            nodes[idx] = seq[start]
        } else {
            val mid = (start + end) / 2
            initialize(start, mid, 2 * idx)
            initialize(mid + 1, end, 2 * idx + 1)
            nodes[idx] = nodes[2 * idx] + nodes[2 * idx + 1]
        }
    }

    /** target 위치를 v로 바꾸고 해당되는 세그트리 부분을 업데이트 (내부 함수) */
    private fun update(start: Int, end: Int, idx: Int, target: Int, v: Long) {
        // 범위에 속하지 않는 경우
        if (target !in start .. end) {
            return
        }
        // 끝까지 가서 찾은 경우
        if (start == end) {
            nodes[idx] = v
            return
        }
        // 더 찾아봐야 하는 경우
        val mid = (start + end) / 2
        update(start, mid, 2 * idx, target, v)
        update(mid + 1, end, 2 * idx + 1, target, v)
        nodes[idx] = nodes[2 * idx] + nodes[2 * idx + 1]
    }

    /** [leftRange, rightRange] 구간에 대한 쿼리 값을 리턴 (내부 함수) */
    private fun get(start: Int, end: Int, idx: Int, leftRange: Int, rightRange: Int): Long {
        // 범위에 속하지 않는 경우
        if (end < leftRange || rightRange < start) {
            return 0L
        }
        // 범위에 완전히 포함되는 경우
        if (leftRange <= start && end <= rightRange) {
            return nodes[idx]
        }
        // 일부만 포함되는 경우
        val mid = (start + end) / 2
        val a = get(start, mid, 2 * idx, leftRange, rightRange)
        val b = get(mid + 1, end, 2 * idx + 1, leftRange, rightRange)
        return a + b
    }

    /** target 위치를 v로 바꾸고 해당되는 세그트리 부분을 업데이트 */
    fun update(target: Int, v: Long) {
        update(0, seq.size - 1, 1, target, v)
    }

    /** [leftRange, rightRange] 구간에 대한 쿼리 값을 리턴 */
    fun get(leftRange: Int, rightRange: Int): Long {
        return get(0, seq.size - 1, 1, leftRange, rightRange)
    }
}