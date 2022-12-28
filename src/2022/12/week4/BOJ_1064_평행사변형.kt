import java.util.*
import kotlin.math.sqrt

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val arrX = IntArray(3)
    val arrY = IntArray(3)

    var st = StringTokenizer(readLine()!!, " ")
    for (i in 0 until 3) {
        arrX[i] = st.nextToken().toInt()
        arrY[i] = st.nextToken().toInt()
    }

    // 세 점이 일직선상에 있어서 평행사변형을 만들 수 없는 경우
    if ((arrY[2] - arrY[1]) * (arrX[1] - arrX[0]) == (arrY[1] - arrY[0]) * (arrX[2] - arrX[1])) {
        println("-1.0")
    }
    // 평행사변형을 만들 수 있는 경우
    else {
        val sideList = mutableListOf<Double>()
        for (i in 0 until 3) {
            for (j in i + 1 until 3) {
                val dy = (arrY[j] - arrY[i]).toDouble()
                val dx = (arrX[j] - arrX[i]).toDouble()
                sideList.add(sqrt(dy * dy + dx * dx))
            }
        }

        sideList.sort()

        val minCircumference = (sideList[0] + sideList[1]) * 2.0
        val maxCircumference = (sideList[1] + sideList[2]) * 2.0
        val answer = maxCircumference - minCircumference

        println(answer)
    }
}