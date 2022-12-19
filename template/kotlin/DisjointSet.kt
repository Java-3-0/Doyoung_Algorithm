class DisjointSet(private val size: Int) {
    private var parent = IntArray(size) { -1 }

    // find 연산
    fun find(a: Int): Int {
        val pa = parent[a]
        return if (pa < 0) {
            a
        } else {
            parent[a] = find(pa)
            parent[a]
        }
    }

    // union 연산
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

    // a를 포함하고 있는 집합의 크기를 리턴
    fun getSizeOfSetIncluding(a: Int): Int {
        return -parent[find(a)]
    }

    // 집합의 개수를 리턴
    fun getNumberOfSets(): Int {
        var ret = 0
        parent.forEach { element -> if (element < 0) ret++ }
        return ret
    }
}