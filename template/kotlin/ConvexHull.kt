import java.util.*
import kotlin.math.abs
import kotlin.math.sqrt

const val INF = 987654321098765432.0

/** 위치 객체  */
data class Position(var x: Double, var y: Double) : Comparable<Position> {
    override fun compareTo(other: Position): Int {
        return compareValuesBy(this, other, { it.x }, { it.y })
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

/** 회전하는 캘리퍼스를 이용하여 볼록다각형에서 두 점 사이의 최대 거리를 구한다  */
fun getMaxDistanceByRotatingCalipers(convexHull: List<Position>): Double {
    val size = convexHull.size
    var idx2 = 1
    var maxDist = -INF
    for (idx1 in 0 until size) {
        while (true) {
            val nextIdx1 = (idx1 + 1) % size
            val nextIdx2 = (idx2 + 1) % size
            val y1 = convexHull[nextIdx1].y - convexHull[idx1].y
            val x1 = convexHull[nextIdx1].x - convexHull[idx1].x
            val y2 = convexHull[nextIdx2].y - convexHull[idx2].y
            val x2 = convexHull[nextIdx2].x - convexHull[idx2].x
            val posA = Position(0.0, 0.0)
            val posB = Position(y1, x1)
            val posC = Position(y2, x2)

            // 반시계 방향인 동안 idx2를 옮긴다.
            if (getCCW(posA, posB, posC) > 0) {
                idx2 = (idx2 + 1) % size
            } else {
                val dist = convexHull[idx1].getDistanceTo(convexHull[idx2])
                maxDist = Math.max(maxDist, dist)
                break
            }
        }
    }
    return maxDist
}

/** 점이 볼록 다각형 내부에 있는지 여부를 리턴한다  */
fun isInside(targetPoint: Position, convexHull: List<Position>): Boolean {
    val size = convexHull.size
    var left = 1
    var right = size - 1
    val originPoint = convexHull[0]

    // 전체 다각형 범위 각도에 포함되지 않는 경우
    if (getCCW(originPoint, convexHull[left], targetPoint) < 0) {
        return false
    }
    if (getCCW(originPoint, targetPoint, convexHull[right]) < 0) {
        return false
    }

    // 이외의 경우, 어느 점과 어느 점 사이 각도에 포함되는지 찾는다 (이분탐색)
    while (left + 1 < right) {
        val mid = (left + right) / 2
        val midPoint = convexHull[mid]
        if (getCCW(originPoint, midPoint, targetPoint) > 0) {
            left = mid
        } else {
            right = mid
        }
    }

    // 그 각도에서 다각형 안쪽인지 바깥쪽인지 판단한다 (경계 포함이면 등호 추가해야 함)
    return getCCW(convexHull[left], convexHull[right], targetPoint) > 0
}

/** 다각형의 넓이를 계산해서 리턴  */
fun getArea(polygon: List<Position>): Double {
    var totalArea = 0.0

    // 삼각형을 만들어서 그 면적을 누적하는 방식으로 계산
    for (i in 2 until polygon.size) {
        val area: Double = 0.5 * getCCW(polygon[0], polygon[i - 1], polygon[i])
        totalArea += area
    }
    totalArea = abs(totalArea)
    return totalArea
}


fun main() = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()
    val tests = readLine().toInt()

    for (i in 1..tests) {
        val (A, B) = readLine().split(" ").map { it.toInt() }
    }

    print(sb)
}

