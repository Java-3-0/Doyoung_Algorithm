const val MAX_N = 50
const val CACHE_EMPTY = -1L
const val MOD = 1000000007L

val cache = Array(MAX_N + 1) { Array(MAX_N + 1) { Array(MAX_N + 1) { LongArray(MAX_N + 1) { CACHE_EMPTY } } } }
var N = 0

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val (n, a, b, c) = readLine()!!.split(" ").map { it.toInt() }
    N = n

    val answer = solve(0, a, b, c)

    println(answer)
}

fun solve(songIdx: Int, a: Int, b: Int, c: Int): Long {
    // 완료 조건
    if (songIdx == N) {
        return if (a == 0 && b == 0 && c == 0) 1L else 0L
    }

    // 가지치기
    if (N - songIdx > a + b + c) {
        return 0L
    }
    if (N < a || N < b || N < c) {
        return 0L
    }

    // memoization
    if (cache[songIdx][a][b][c] != CACHE_EMPTY) {
        return cache[songIdx][a][b][c]
    }

    // 새로 계산
    var ret = 0L
    for (bitmask in 1 .. 7) {
        val da = (bitmask and 1)
        val db = ((bitmask shr 1) and 1)
        val dc = ((bitmask shr 2) and 1)

        if (a - da >= 0 && b - db >= 0 && c - dc >= 0) {
            ret += solve(songIdx + 1, a - da, b - db, c - dc)
            ret %= MOD
        }
    }

    cache[songIdx][a][b][c] = ret
    return ret
}
