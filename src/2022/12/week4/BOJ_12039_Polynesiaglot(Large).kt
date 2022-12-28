const val MAX_C = 50
const val MAX_V = 50
const val MAX_L = 500
const val MOD = 1000000007
const val CACHE_EMPTY = -1L

val cache = Array(MAX_C + 1) { Array(MAX_V + 1) { LongArray(MAX_L + 1) { CACHE_EMPTY } } }

fun main(): Unit = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    val tests = readLine()!!.toInt()
    for (tc in 1 .. tests) {
        val (C, V, L) = readLine()!!.split(" ").map { it.toInt() }

        val answer = solve(C, V, L)
        sb.append("Case #$tc: $answer\n")
    }

    print(sb)
}

fun solve(c: Int, v: Int, l: Int): Long {
    if (l == 0) {
        return 1L
    }
    if (l < 0) {
        return 0L
    }
    if (cache[c][v][l] != CACHE_EMPTY) {
        return cache[c][v][l]
    }

    val useVowel = (v.toLong() * solve(c, v, l - 1)) % MOD
    val useConsonant = (((c.toLong() * v.toLong()) % MOD) * solve(c, v, l - 2)) % MOD
    val ret = (useVowel + useConsonant) % MOD
    cache[c][v][l] = ret

    return ret
}