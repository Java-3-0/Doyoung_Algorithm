import java.util.*

const val GRID_SIZE = 9
const val MAX_NUM = 9
const val EMPTY = 0
val grid = Array(GRID_SIZE) { IntArray(GRID_SIZE) { EMPTY } }
val isUsedDomino = Array(MAX_NUM + 1) { BooleanArray(MAX_NUM + 1) { false } }
var foundAnswer = false
val sb = StringBuilder()
var tc = 0

fun main(): Unit = with(System.`in`.bufferedReader()) {
    var st: StringTokenizer

    while (true) {
        tc++

        // 메모리 초기화
        initMemory()

        // 채워져 있는 도미노 개수 입력
        val N = readLine()!!.toInt()
        if (N == 0) {
            break
        }

        // 채워져 있는 도미노 입력
        repeat(N) {
            st = StringTokenizer(readLine()!!, " ")
            val num1 = st.nextToken().toInt()
            val pos1 = st.nextToken()
            val num2 = st.nextToken().toInt()
            val pos2 = st.nextToken()

            val y1 = pos1[0].minus('A')
            val x1 = pos1[1].minus('1')
            val y2 = pos2[0].minus('A')
            val x2 = pos2[1].minus('1')

            grid[y1][x1] = num1
            grid[y2][x2] = num2

            isUsedDomino[num1][num2] = true
            isUsedDomino[num2][num1] = true
        }

        // 같은 수로 이루어진 도미노는 못 쓴다
        for (i in 1 .. MAX_NUM) {
            isUsedDomino[i][i] = true
        }

        // 채워져 있는 숫자 입력
        st = StringTokenizer(readLine()!!, " ")
        for (num in 1 .. 9) {
            val pos = st.nextToken()
            val y = pos[0].minus('A')
            val x = pos[1].minus('1')
            grid[y][x] = num
        }

        // 첫 칸부터 도미노 채우기 시도
        solve(0)
    }

    print(sb)
}

fun solve(idx: Int) {
    if (foundAnswer) {
        return
    }

    if (idx == GRID_SIZE * GRID_SIZE) {
        foundAnswer = true

        sb.append("Puzzle $tc\n")
        sb.append(gridToString())
        return
    }

    val y = idx / GRID_SIZE
    val x = idx % GRID_SIZE

    // 이미 채워진 칸이면 다음 칸으로
    if (grid[y][x] != EMPTY) {
        solve(idx + 1)
    }

    // 채워야 하는 칸이면
    else {
        // 가로로 두 칸이 비어있으면 가로로 채우기 시도
        if (x + 1 < GRID_SIZE && grid[y][x + 1] == EMPTY) {
            for (num1 in 1 .. MAX_NUM) {
                for (num2 in 1 .. MAX_NUM) {
                    if (!isUsedDomino[num1][num2] && canFill(y, x, num1) && canFill(y, x + 1, num2)) {
                        fill(y, x, num1, y, x + 1, num2)
                        solve(idx + 1)
                        undo(y, x, num1, y, x + 1, num2)
                    }
                }
            }
        }

        // 세로로 두 칸이 비어있으면 세로로 채우기 시도
        if (y + 1 < GRID_SIZE && grid[y + 1][x] == EMPTY) {
            for (num1 in 1 .. MAX_NUM) {
                for (num2 in 1 .. MAX_NUM) {
                    if (!isUsedDomino[num1][num2] && canFill(y, x, num1) && canFill(y + 1, x, num2)) {
                        fill(y, x, num1, y + 1, x, num2)
                        solve(idx + 1)
                        undo(y, x, num1, y + 1, x, num2)
                    }
                }
            }
        }
    }
}

/** grid 의 (y1, x1) 을 num1 으로 채우고, (y2, x2) 를 num2 로 채운다 */
fun fill(y1: Int, x1: Int, num1: Int, y2: Int, x2: Int, num2: Int) {
    grid[y1][x1] = num1
    grid[y2][x2] = num2
    isUsedDomino[num1][num2] = true
    isUsedDomino[num2][num1] = true
}

/** fill 을 하기 이전으로 되돌린다 */
fun undo(y1: Int, x1: Int, num1: Int, y2: Int, x2: Int, num2: Int) {
    grid[y1][x1] = EMPTY
    grid[y2][x2] = EMPTY
    isUsedDomino[num1][num2] = false
    isUsedDomino[num2][num1] = false
}


/** grid 의 (targetY, targetX) 위치를 num 으로 채울 수 있는지 여부를 리턴 */
fun canFill(targetY: Int, targetX: Int, num: Int): Boolean {
    // 세로 탐색
    for (y in 0 until GRID_SIZE) {
        if (grid[y][targetX] == num) {
            return false
        }
    }

    // 가로 탐색
    for (x in 0 until GRID_SIZE) {
        if (grid[targetY][x] == num) {
            return false
        }
    }

    // 3 x 3 사각형 탐색
    val startY = targetY - (targetY % 3)
    val startX = targetX - (targetX % 3)
    for (y in startY .. startY + 2) {
        for (x in startX .. startX + 2) {
            if (grid[y][x] == num) {
                return false
            }
        }
    }

    // 모든 탐색에 같은 수가 없으면 채우기 가능
    return true
}

/** 테스트 케이스마다 전역변수 메모리 초기화 */
fun initMemory() {
    for (i in grid.indices) {
        Arrays.fill(grid[i], EMPTY)
    }
    for (i in isUsedDomino.indices) {
        Arrays.fill(isUsedDomino[i], false)
    }
    foundAnswer = false
}

/** grid 의 현재 상태를 출력 형식에 맞는 String 으로 변환해서 리턴 */
fun gridToString(): String {
    val sbTmp = StringBuilder()
    for (y in 0 until GRID_SIZE) {
        for (x in 0 until GRID_SIZE) {
            sbTmp.append(grid[y][x])
        }
        sbTmp.append("\n")
    }

    return sbTmp.toString()
}