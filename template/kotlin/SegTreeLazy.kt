import kotlin.math.ceil
import kotlin.math.log2

data class SegTreeLazy(val seq: List<Long>) {
    private val treeSize = 1 shl (1 + ceil(log2(seq.size.toDouble()))).toInt()
    private val nodes = LongArray(treeSize)
    private val lazy = LongArray(treeSize)

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

    /** lazy 를 자식에게 전달하고 처리 */
    private fun processLazy(start: Int, end: Int, idx: Int) {
        if (lazy[idx] != 0L) {
            // 자식에게 lazy 값 전달
            if (start != end) {
                lazy[2 * idx] += lazy[idx]
                lazy[2 * idx + 1] += lazy[idx]
            }

            // 본인의 lazy 값 처리
            nodes[idx] += lazy[idx] * (end - start + 1).toLong()
            lazy[idx] = 0L
        }
    }

    /** [leftRange, rightRange] 구간에 delta 를 더할 때 해당되는 세그트리 부분을 업데이트 (내부 함수) */
    private fun update(start: Int, end: Int, idx: Int, leftRange: Int, rightRange: Int, delta: Long) {
        // lazy 처리
        processLazy(start, end, idx)

        // 범위에 포함되지 않는 경우
        if (end < leftRange || rightRange < start) {
            return
        }
        // 범위에 완전히 포함되는 경우
        if (leftRange <= start && end <= rightRange) {
            lazy[idx] = delta
            processLazy(start, end, idx)
            return
        }
        // 범위에 일부만 포함되는 경우
        val mid = (start + end) / 2
        update(start, mid, 2 * idx, leftRange, rightRange, delta)
        update(mid + 1, end, 2 * idx + 1, leftRange, rightRange, delta)
        nodes[idx] = nodes[2 * idx] + nodes[2 * idx + 1]
    }

    /** [leftRange, rightRange] 구간에 대한 쿼리 값을 리턴 (내부 함수) */
    private fun get(start: Int, end: Int, idx: Int, leftRange: Int, rightRange: Int): Long {
        // lazy 처리
        processLazy(start, end, idx)

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

    /** [leftRange, rightRange] 구간에 delta 를 더할 때 해당되는 세그트리 부분을 업데이트 */
    fun update(leftRange: Int, rightRange: Int, delta: Long) {
        update(0, seq.size - 1, 1, leftRange, rightRange, delta)
    }

    /** [leftRange, rightRange] 구간에 대한 쿼리 값을 리턴 */
    fun get(leftRange: Int, rightRange: Int): Long {
        return get(0, seq.size - 1, 1, leftRange, rightRange)
    }
}