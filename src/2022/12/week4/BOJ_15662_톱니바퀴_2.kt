import java.util.*

const val TOP = 0
const val RIGHT = 2
const val BOTTOM = 4
const val LEFT = 6

fun main() = with(System.`in`.bufferedReader()) {
    val numberOfGears = readLine()!!.toInt()

    val gears = Array(numberOfGears) { LinkedList<Char>() }
    for (i in 0 until numberOfGears) {
        readLine()!!.forEach { c -> gears[i].add(c) }
    }


    val K = readLine()!!.toInt()
    repeat(K) {
        val st = StringTokenizer(readLine()!!, " ")
        val startIdx = st.nextToken().toInt() - 1
        val direction = st.nextToken().toInt()

        val isSameToNext = BooleanArray(numberOfGears - 1)
        for (i in 1 until numberOfGears) {
            isSameToNext[i - 1] = gears[i - 1][RIGHT] == gears[i][LEFT]
        }

        var curDir = direction
        for (idx in startIdx until numberOfGears) {
            if (curDir == 1) {
                gears[idx].addFirst(gears[idx].pollLast())
            } else {
                gears[idx].addLast(gears[idx].pollFirst())
            }

            if (idx == numberOfGears - 1 || isSameToNext[idx]) {
                break
            } else {
                curDir = -curDir
            }
        }

        curDir = -direction
        for (idx in startIdx - 1 downTo 0) {
            if (isSameToNext[idx]) {
                break
            }
            else {
                if (curDir == 1) {
                    gears[idx].addFirst(gears[idx].pollLast())
                } else {
                    gears[idx].addLast(gears[idx].pollFirst())
                }
                curDir = -curDir
            }
        }
    }

    var answer = 0
    for (i in 0 until numberOfGears) {
        answer += gears[i][TOP].minus('0')
    }

    println(answer)
}