import java.util.*


fun main() = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()
    val (N, M) = readLine().split(" ").map { it.toInt() }
    val positionListA = mutableListOf<Position>()
    val positionListB = mutableListOf<Position>()

    repeat(N) {
        val (x, y) = readLine().split(" ").map { it.toDouble() }
        positionListA.add(Position(x, y))
    }

    repeat(M) {
        val (x, y) = readLine().split(" ").map { it.toDouble() }
        positionListB.add(Position(x, y))
    }

    val positionListAll = mutableListOf<Position>()
    for (posA in positionListA) {
        for (posB in positionListB) {
            positionListAll.add(Position(posA.x + posB.x, posA.y + posB.y))
        }
    }

    val convexHull = getConvexHull(positionListAll)

    sb.append("${convexHull.size}\n")

    val startIdx = convexHull.indexOf(convexHull.min())
    for (i in convexHull.indices) {
        val pos = convexHull[(startIdx + i) % convexHull.size]
        sb.append("${pos.x.toLong()} ${pos.y.toLong()}\n")
    }

    print(sb)
}

/** 위치 객체  */
data class Position(var x: Double, var y: Double) : Comparable<Position> {
    override fun compareTo(other: Position): Int {
        if (this.x == other.x) {
            return this.y.compareTo(other.y)
        }
        return this.x.compareTo(other.x)
    }
}

/** 세 점의 외적 계산. 반시계 방향이면 양수, 일직선이면 0, 시계 방향이면 음수 리턴 */
fun getCCW(a: Position, b: Position, c: Position): Double {
    val sum1 = a.x * b.y + b.x * c.y + c.x * a.y
    val sum2 = a.y * b.x + b.y * c.x + c.y * a.x
    return sum1 - sum2
}

/** 컨벡스 헐에 포함되는 정점들을 리스트에 담아서 리턴 (결과 리스트 순서는 반시계 방향) */
fun getConvexHull(positions: List<Position>): List<Position> {
    val size = positions.size

    // 위치들을 x좌표 오름차순, y좌표 오름차순 정렬
    val sortedPositions = positions.sorted()

    // 스택 생성
    val lower = Stack<Position>()
    val upper = Stack<Position>()

    // 아래 껍질 계산
    for (i in 0 until size) {
        while (lower.size > 1 && getCCW(lower[lower.size - 2], lower[lower.size - 1], sortedPositions[i]) >= 0) {
            lower.pop()
        }
        lower.add(sortedPositions[i])
    }

    // 위 껍질 계산
    for (i in size - 1 downTo 0) {
        while (upper.size > 1 && getCCW(upper[upper.size - 2], upper[upper.size - 1], sortedPositions[i]) >= 0) {
            upper.pop()
        }
        upper.add(sortedPositions[i])
    }

    // 시작점, 끝점에서의 중복 제거
    lower.pop()
    upper.pop()

    // 아래 껍질과 위 껍질을 합쳐서 정답 계산
    val ret: MutableList<Position> = ArrayList()
    while (!lower.isEmpty()) {
        ret.add(lower.pop())
    }
    while (!upper.isEmpty()) {
        ret.add(upper.pop())
    }
    return ret
}