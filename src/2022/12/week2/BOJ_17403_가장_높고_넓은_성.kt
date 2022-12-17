import java.util.*
import kotlin.math.abs

fun main() = with(System.`in`.bufferedReader()) {
    // 표지판 개수 입력
    val N = readLine().toInt()

    // 모든 표지판 위치 입력받아서 positionList 에 넣기
    val positionList = mutableListOf<Position>()
    repeat(N) {
        val (x, y) = readLine().split(" ").map { it.toDouble() }
        positionList.add(Position(x, y))
    }

    // 위치 -> 위치 번호로의 매핑 생성
    val mapPositionToIdx = HashMap<Position, Int>()
    for (i in positionList.indices) {
        mapPositionToIdx[positionList[i]] = i
    }

    // 바깥쪽부터 볼록 껍질 한 층씩 만들어가기
    val answerArr = IntArray(N) { 0 }
    var floorCnt = 0
    while (positionList.size >= 3) {
        // 층수 갱신
        floorCnt++

        // 정점 리스트에서 볼록 껍질 찾기
        val convexHull = getConvexHull(positionList)

        // 넓이가 0 이상이라면 answerArr 갱신
        if (getArea(convexHull) > 0.0) {
            for (boundaryPosition in convexHull) {
                answerArr[mapPositionToIdx[boundaryPosition]!!] = floorCnt
            }
        }

        // 정점 리스트에서 제거
        for (boundaryPosition in convexHull) {
            positionList.remove(boundaryPosition)
        }
    }

    // 정답 출력
    val sb = StringBuilder()
    for (answer in answerArr) {
        sb.append("$answer ")
    }
    println(sb)

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