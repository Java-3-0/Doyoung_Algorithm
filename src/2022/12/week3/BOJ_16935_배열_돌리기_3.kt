import java.util.*

var H = 0
var W = 0
var grid = arrayOf(intArrayOf())

fun main() = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    // 그리드 크기 입력
    var st = StringTokenizer(readLine()!!, " ")
    H = st.nextToken().toInt()
    W = st.nextToken().toInt()

    // 그리드 입력
    grid = Array(H) { IntArray(W) { 0 } }
    for (y in 0 until H) {
        st = StringTokenizer(readLine()!!, " ")
        for (x in 0 until W) {
            grid[y][x] = st.nextToken().toInt()
        }
    }

    // 연산 입력
    val operationList = readLine()!!.split(" ").map { it.toInt() }

    // 연산 처리
    for (operation in operationList) {
        when (operation) {
            1 -> doOperation1()
            2 -> doOperation2()
            3 -> doOperation3()
            4 -> doOperation4()
            5 -> doOperation5()
            6 -> doOperation6()
        }
    }

    // 출력
    for (y in 0 until H) {
        for (x in 0 until W) {
            sb.append("${grid[y][x]} ")
        }
        sb.append("\n")
    }

    print(sb)
}

fun doOperation1() {
    val halfH = H / 2
    for (y in 0 until halfH) {
        for (x in 0 until W) {
            val tmp = grid[y][x]
            grid[y][x] = grid[H - 1 - y][x]
            grid[H - 1 - y][x] = tmp
        }
    }
}

fun doOperation2() {
    val halfW = W / 2
    for (y in 0 until H) {
        for (x in 0 until halfW) {
            val tmp = grid[y][x]
            grid[y][x] = grid[y][W - 1 - x]
            grid[y][W - 1 - x] = tmp
        }
    }
}

fun doOperation3() {
    val nextH = W
    val nextW = H

    val nextGrid = Array(nextH) { IntArray(nextW) }
    for (y in 0 until H) {
        for (x in 0 until W) {
            nextGrid[x][nextW - 1 - y] = grid[y][x]
        }
    }

    H = nextH
    W = nextW
    grid = nextGrid
}


fun doOperation4() {
    val nextH = W
    val nextW = H

    val nextGrid = Array(nextH) { IntArray(nextW) }
    for (y in 0 until H) {
        for (x in 0 until W) {
            nextGrid[nextH - 1 - x][y] = grid[y][x]
        }
    }

    H = nextH
    W = nextW
    grid = nextGrid
}


fun doOperation5() {
    val halfH = H / 2
    val halfW = W / 2

    for (y in 0 until halfH) {
        for (x in 0 until halfW) {
            val tmp = grid[y][x]
            grid[y][x] = grid[y + halfH][x]
            grid[y + halfH][x] = grid[y + halfH][x + halfW]
            grid[y + halfH][x + halfW] = grid[y][x + halfW]
            grid[y][x + halfW] = tmp
        }
    }
}

fun doOperation6() {
    val halfH = H / 2
    val halfW = W / 2

    for (y in 0 until halfH) {
        for (x in 0 until halfW) {
            val tmp = grid[y][x]
            grid[y][x] = grid[y][x + halfW]
            grid[y][x + halfW] = grid[y + halfH][x + halfW]
            grid[y + halfH][x + halfW] = grid[y + halfH][x]
            grid[y + halfH][x] = tmp
        }
    }
}
