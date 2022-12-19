import kotlin.math.min

data class Person(var from: Long, var to: Long) : Comparable<Person> {
    override fun compareTo(other: Person): Int {
        if (this.from == other.from) {
            return -(this.to.compareTo(other.to))
        }
        return -(this.from.compareTo(other.from))
    }
}

fun main() = with(System.`in`.bufferedReader()) {
    val (N, M) = readLine().split(" ").map { it.toInt() }

    // 역방향으로 가는 사람들의 리스트를 만든다
    val personList = mutableListOf<Person>()
    for (i in 0 until N) {
        val (from, to) = readLine().split(" ").map { it.toLong() }
        if (from > to) {
            personList.add(Person(from, to))
        }
    }

    // 정렬
    personList.sort()

    // 역방향으로 가야 하는 거리를 구한다
    var answer = M.toLong()
    var left = M.toLong()
    var right = M.toLong()
    for (person in personList) {
        val nowRight = person.from
        val nowLeft = person.to

        // 새로운 선분이 생긴 경우
        if (nowRight < left) {
            answer += (right - left) * 2L
            right = nowRight
            left = nowLeft
        }
        // 기존 선분에서 이어지는 경우
        else {
            left = min(nowLeft, left)
        }
    }

    answer += (right - left) * 2L

    println(answer)

}