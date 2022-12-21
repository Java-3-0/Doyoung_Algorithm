import java.util.*

const val MAX_H = 10
const val MAX_W = 10
const val EMPTY = '.'
const val WALL = '#'
const val HOLE = 'O'
const val RED = 'R'
const val BLUE = 'B'
const val UP = 0
const val RIGHT = 1
const val DOWN = 2
const val LEFT = 3
const val IMPOSSIBLE = -1

val dy = intArrayOf(-1, 0, 1, 0)
val dx = intArrayOf(0, 1, 0, -1)

var H = 0
var W = 0
val grid = Array(MAX_H) { CharArray(MAX_W) }
val queue = LinkedList<Status>()
val isVisited = Array(MAX_H) { Array(MAX_W) { Array(MAX_H) { BooleanArray(MAX_W) { false } } } }

// 위치 객체
data class Position(var y: Int, var x: Int) {
    fun isInRange(): Boolean {
        return (y in 0 until H) && (x in 0 until W)
    }

    fun getNextPosition(dir: Int): Position {
        return Position(y + dy[dir], x + dx[dir])
    }

    fun getGridVal(): Char {
        return grid[y][x]
    }
}

// 큐에 넣을 상태 객체
data class Status(var redPosition: Position, var bluePosition: Position) {
    fun isVisited(): Boolean {
        return isVisited[redPosition.y][redPosition.x][bluePosition.y][bluePosition.x]
    }

    fun visit() {
        isVisited[redPosition.y][redPosition.x][bluePosition.y][bluePosition.x] = true
    }
}

fun main() = with(System.`in`.bufferedReader()) {
    // 그리드 크기 입력
    var st = StringTokenizer(readLine()!!, " ")
    H = st.nextToken().toInt()
    W = st.nextToken().toInt()

    // 그리드 입력 받으면서 빨간 공과 파란 공의 초기 위치 찾아서 저장
    var redPosition = Position(0, 0)
    var bluePosition = Position(0, 0)
    for (y in 0 until H) {
        val line = readLine()!!
        for (x in 0 until W) {
            grid[y][x] = line[x]
            when (grid[y][x]) {
                RED -> {
                    redPosition.y = y
                    redPosition.x = x
                    grid[y][x] = EMPTY
                }

                BLUE -> {
                    bluePosition.y = y
                    bluePosition.x = x
                    grid[y][x] = EMPTY
                }
            }
        }
    }

    // bfs 수행
    val answer = bfs(Status(redPosition, bluePosition))

    println(answer)
}

// bfs 를 수행하며 공을 빼내는 방법을 찾는다
fun bfs(startStatus: Status): Int {
    // 시작 상태 처리
    startStatus.visit()
    queue.offer(startStatus)

    // bfs
    var depth = 0
    while (queue.isNotEmpty()) {
        val size = queue.size

        for (i in 0 until size) {
            // 현재 상태 가져오기
            val now = queue.poll()!!
            val nowRed = now.redPosition
            val nowBlue = now.bluePosition

            // 파란 공이 구멍에 빠지면 실패
            if (nowBlue.getGridVal() == HOLE) {
                continue
            }
            // 빨간 공만 구멍에 빠지면 성공
            else if (nowRed.getGridVal() == HOLE) {
                return depth
            }
            // 다음 상태 탐색
            else {
                for (dir in 0 until 4) {
                    // 진행 방향으로 앞쪽에 있는 공이 먼저 움직여야 함
                    if (shouldMoveBlueFirst(nowRed, nowBlue, dir)) {
                        val nextBlue = findPositionToStop(nowBlue, dir, nowRed)
                        val nextRed = findPositionToStop(nowRed, dir, nextBlue)
                        val nextStatus = Status(nextBlue, nextRed)
                        if (!nextStatus.isVisited()) {
                            nextStatus.visit()
                            queue.offer(Status(nextRed, nextBlue))
                        }
                    } else {
                        val nextRed = findPositionToStop(nowRed, dir, nowBlue)
                        val nextBlue = findPositionToStop(nowBlue, dir, nextRed)
                        val nextStatus = Status(nextBlue, nextRed)
                        if (!nextStatus.isVisited()) {
                            nextStatus.visit()
                            queue.offer(Status(nextRed, nextBlue))
                        }
                    }
                }
            }
        }

        depth++
    }

    return IMPOSSIBLE
}

// dir 방향으로 bluePosition 이 redPosition 보다 앞에 있는지 여부를 리턴
fun shouldMoveBlueFirst(redPosition: Position, bluePosition: Position, dir: Int): Boolean {
    if (dir == LEFT && redPosition.x > bluePosition.x) {
        return true
    }
    if (dir == UP && redPosition.y > bluePosition.y) {
        return true
    }
    if (dir == RIGHT && redPosition.x < bluePosition.x) {
        return true
    }
    if (dir == DOWN && redPosition.y < bluePosition.y) {
        return true
    }

    return false
}

// 다른 공이 otherBall 위치에 있을 때 start 위치에서 dir 방향으로 출발한 공이 멈추는 위치를 리턴
fun findPositionToStop(start: Position, dir: Int, otherBall: Position): Position {
    var cur = start.copy()

    // 한 칸씩 이동해보면서 멈출 칸을 찾는다
    while (true) {
        val next = cur.getNextPosition(dir)
        // 다음 위치가 범위 밖이면 현재 위치에 멈춘다
        if (!next.isInRange()) {
            return cur
        } else {
            val gridVal = next.getGridVal()

            // 다음 위치가 벽이면 현재 위치에 멈춘다
            if (gridVal == WALL) {
                return cur
            }
            // 다음 위치가 구멍이면 다음 위치에 멈춘다
            else if (gridVal == HOLE) {
                return next
            }
            // 다음 위치가 다른 공의 위치와 같으면 현재 위치에 멈춘다
            else if (next.y == otherBall.y && next.x == otherBall.x) {
                return cur
            }
        }

        cur = next
    }
}