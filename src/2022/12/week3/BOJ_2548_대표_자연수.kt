fun main() = with(System.`in`.bufferedReader()) {
    val N = readLine().toInt()
    val seq = readLine().split(" ").map { it.toInt() }.sorted()

    val answer = seq[(seq.size - 1) / 2]
    println(answer)
}