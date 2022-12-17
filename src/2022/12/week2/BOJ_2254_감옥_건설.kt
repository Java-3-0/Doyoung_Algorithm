import java.util.*

fun main() = with(System.`in`.bufferedReader()) {
    val st = StringTokenizer(readLine(), " ")
    val N = st.nextToken().toInt()
    val prisonPosition = Position(st.nextToken().toDouble(), st.nextToken().toDouble())

    var positionList = mutableListOf<Position>()
    repeat(N) {
        val (x, y) = readLine().split(" ").map { it.toDouble() }
        positionList.add(Position(x, y))
    }

    var answer = 0
    while (positionList.size >= 3) {
        // 정점 리스트에서 볼록 껍질 찾기
        val convexHull = getConvexHull(positionList)

        // 볼록 껍질이 감옥을 포함하는 경우
        if (isInside(prisonPosition, convexHull)) {
            // 정답 갱신
            answer++

            // 이 볼록 껍질 안의 점만 남기기
            for (boundaryPosition in convexHull) {
                positionList.remove(boundaryPosition)
            }
        } else {
            break
        }
    }

    println(answer)

}

/** 위치 객체  */
data class Position(var x: Double, var y: Double) : Comparable<Position> {
    override fun compareTo(other: Position): Int {
        return compareValuesBy(this, other, { it.x }, { it.y })
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
