import kotlin.math.sqrt

const val VERTICES = 8
val SQRT_HALF = 1.0 / sqrt(2.0)
val yMult = arrayOf(1.0, SQRT_HALF, 0.0, -SQRT_HALF, -1.0, -SQRT_HALF, 0.0, SQRT_HALF)
val xMult = arrayOf(0.0, SQRT_HALF, 1.0, SQRT_HALF, 0.0, -SQRT_HALF, -1.0, -SQRT_HALF)

val permutation = IntArray(VERTICES)
val isUsed = BooleanArray(VERTICES) { false }
var answer = 0

data class Position(var x: Double, var y: Double)

fun main() = with(System.`in`.bufferedReader()) {
    val numList = readLine().split(" ").map { it.toDouble() }

    makePermutations(0, numList)

    println(answer)
}

/** 모든 순열 만들어보면서 answer 갱신 */
fun makePermutations(cnt: Int, numList: List<Double>) {
    if (cnt == VERTICES) {
        val positionList = mutableListOf<Position>()
        for (i in 0 until VERTICES) {
            positionList.add(Position(xMult[i] * numList[permutation[i]], yMult[i] * numList[permutation[i]]))
        }

        if (isConvexPolygon(positionList)) {
            answer++
        }
    }

    for (num in 0 until VERTICES) {
        if (!isUsed[num]) {
            permutation[cnt] = num
            isUsed[num] = true
            makePermutations(cnt + 1, numList)
            isUsed[num] = false
        }
    }
}


/** 세 점의 외적 계산. 반시계 방향이면 양수, 일직선이면 0, 시계 방향이면 음수 리턴 */
fun getCCW(a: Position, b: Position, c: Position): Double {
    val sum1 = a.x * b.y + b.x * c.y + c.x * a.y
    val sum2 = a.y * b.x + b.y * c.x + c.y * a.x
    return sum1 - sum2
}

/** 볼록 다각형 여부 리턴 */
fun isConvexPolygon(positionList: List<Position>): Boolean {
    for (i in 0 until VERTICES) {
        val posA = positionList[i]
        val posB = positionList[(i + 1) % VERTICES]
        val posC = positionList[(i + 2) % VERTICES]

        if (getCCW(posA, posB, posC) >= 0.0) {
            return false
        }

    }
    return true
}
