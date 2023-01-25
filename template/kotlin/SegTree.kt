import kotlin.math.ceil
import kotlin.math.log2

class SegTree(private val seq: List<Long>) {
    private val treeSize = 1 shl (1 + ceil(log2(seq.size.toDouble()))).toInt()
    private val nodes = LongArray(treeSize)

    init {
        initialize(0, seq.size - 1, 1)
    }

    private fun initialize(start: Int, end: Int, idx: Int) {
        if (start == end) {
            nodes[idx] = seq[start]
        } else {
            val mid = (start + end) / 2
            initialize(start, mid, 2 * idx)
            initialize(mid + 1, end, 2 * idx + 1)
            nodes[idx] = calcParentNode(nodes[2 * idx], nodes[2 * idx + 1])
        }
    }

    private fun calcParentNode(left: Long, right: Long): Long {
        return left + right
    }

    private fun update(start: Int, end: Int, idx: Int, target: Int, v: Long) {
        if (target !in start .. end) {
            return
        }
        if (start == end) {
            nodes[idx] += v
            return
        }
        val mid = (start + end) / 2
        update(start, mid, 2 * idx, target, v)
        update(mid + 1, end, 2 * idx + 1, target, v)
        nodes[idx] = calcParentNode(nodes[2 * idx], nodes[2 * idx + 1])
    }

    private fun get(start: Int, end: Int, idx: Int, leftRange: Int, rightRange: Int): Long {
        if (end < leftRange || rightRange < start) {
            return 0
        }
        if (leftRange <= start && end <= rightRange) {
            return nodes[idx]
        }
        val mid = (start + end) / 2
        val a = get(start, mid, 2 * idx, leftRange, rightRange)
        val b = get(mid + 1, end, 2 * idx + 1, leftRange, rightRange)
        return calcParentNode(a, b)
    }

    fun update(target: Int, v: Long) {
        update(0, seq.size - 1, 1, target, v)
    }

    fun get(leftRange: Int, rightRange: Int): Long {
        return get(0, seq.size - 1, 1, leftRange, rightRange)
    }
}