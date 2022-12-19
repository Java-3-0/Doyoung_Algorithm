import kotlin.math.max

class Item(val num: Int, val originalIdx: Int) : Comparable<Item> {
    override fun compareTo(other: Item): Int {
        if (this.num == other.num) {
            return this.originalIdx.compareTo(other.originalIdx)
        }
        return this.num.compareTo(other.num)
    }
}

fun main() = with(System.`in`.bufferedReader()) {
    val N = readLine().toInt()
    val itemList = MutableList(N) { i -> Item(readLine().toInt(), i) }

    itemList.sort()

    var maxMoveLeft = 0
    itemList.forEachIndexed { index, item -> maxMoveLeft = max(maxMoveLeft, item.originalIdx - index) }

    val answer = maxMoveLeft + 1
    println(answer)
}