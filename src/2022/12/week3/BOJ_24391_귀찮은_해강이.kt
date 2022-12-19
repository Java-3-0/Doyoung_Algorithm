data class DisjointSet(val size: Int) {
    var parent: IntArray = IntArray(size) { -1 }

    fun find(a: Int): Int {
        val pa = parent[a]
        return if (pa < 0) {
            a
        } else find(pa).also { parent[a] = it }
    }

    fun union(a: Int, b: Int): Boolean {
        val aRoot = find(a)
        val bRoot = find(b)
        if (aRoot == bRoot) {
            return false
        }
        parent[aRoot] += parent[bRoot]
        parent[bRoot] = aRoot
        return true
    }
}

fun main() = with(System.`in`.bufferedReader()) {
    val (N, M) = readLine().split(" ").map { it.toInt() }

    val disjointSet = DisjointSet(N)
    repeat(M) {
        val (a, b) = readLine().split(" ").map { it.toInt() - 1 }
        disjointSet.union(a, b)
    }

    val timetable = readLine().split(" ").map { it.toInt() - 1 }

    var answer = 0
    var prev = disjointSet.find(timetable[0])
    for (i in 1 until timetable.size) {
        val cur = disjointSet.find(timetable[i])

        if (prev != cur) {
            answer++
        }

        prev = cur
    }

    println(answer)

}