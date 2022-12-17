import java.util.*
import kotlin.math.sqrt

fun main() = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()
    val tests = readLine().toInt()

    repeat(tests) {
        // 정점 개수 입력
        val N = readLine().toInt()

        // 정점 입력받아서 리스트에 담기
        val positionList = mutableListOf<Position>()
        repeat(N) {
            val (x, y) = readLine().split(" ").map { it.toDouble() }
            positionList.add(Position(x, y))
        }

        // 정점 리스트에서 볼록 껍질 찾기
        val convexHull = getConvexHull(positionList)

        // 볼록 껍질 돌면서 거리 합산
        var answer = 0.0
        for (i in convexHull.indices) {
            val distance = convexHull[i].getDistanceTo(convexHull[(i + 1) % convexHull.size])
            answer += distance
        }

        sb.append(String.format("%.2f", answer)).append("\n")
    }

    print(sb)
}

/** 위치 객체  */
data class Position(var x: Double, var y: Double) : Comparable<Position> {
    override fun compareTo(other: Position): Int {
        return compareValuesBy(this, other, { -it.y }, { it.x })
    }

    fun getDistanceTo(p: Position): Double {
        val dy = y - p.y
        val dx = x - p.x
        return sqrt(dy * dy + dx * dx)
    }
}


/** 세 점의 외적 계산. 반시계 방향이면 양수, 일직선이면 0, 시계 방향이면 음수 리턴 */
fun getCCW(a: Position, b: Position, c: Position): Double {
    val sum1 = a.x * b.y + b.x * c.y + c.x * a.y
    val sum2 = a.y * b.x + b.y * c.x + c.y * a.x
    return sum1 - sum2
}

/** 컨벡스 헐에 포함되는 정점들을 리스트에 담아서 리턴 (결과 리스트 순서는 반시계 방향) */
fun getConvexHull(positionList: List<Position>): List<Position> {
    val size = positionList.size

    // 위치들을 x좌표 오름차순, y좌표 오름차순 정렬
    val sortedPositionList = positionList.sorted()

    // 스택 생성
    val lower = Stack<Position>()
    val upper = Stack<Position>()

    // 아래 껍질 계산
    for (i in 0 until size) {
        while (lower.size > 1 && getCCW(lower[lower.size - 2], lower[lower.size - 1], sortedPositionList[i]) >= 0) {
            lower.pop()
        }
        lower.add(sortedPositionList[i])
    }

    // 위 껍질 계산
    for (i in size - 1 downTo 0) {
        while (upper.size > 1 && getCCW(upper[upper.size - 2], upper[upper.size - 1], sortedPositionList[i]) >= 0) {
            upper.pop()
        }
        upper.add(sortedPositionList[i])
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