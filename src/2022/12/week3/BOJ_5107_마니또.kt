val edgeMap = HashMap<String, String>()
val visitSet = HashSet<String>()

fun main() = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    var tcNum = 0
    while (true) {
        tcNum++

        edgeMap.clear()
        visitSet.clear()

        val N = readLine()!!.toInt()
        if (N == 0) {
            break
        }

        repeat(N) {
            val (from, to) = readLine()!!.split(" ")
            edgeMap[from] = to
        }

        var answer = 0
        for (key in edgeMap.keys) {
            if (!visitSet.contains(key)) {
                dfs(key)
                answer++
            }
        }

        sb.append("$tcNum $answer\n")
    }

    print(sb)
}

fun dfs(here: String) {
    if (visitSet.contains(here)) {
        return
    }

    visitSet.add(here)
    if (edgeMap.containsKey(here)) {
        dfs(edgeMap[here]!!)
    }
}
