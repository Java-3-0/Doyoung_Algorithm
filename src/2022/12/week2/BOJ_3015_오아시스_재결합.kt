import java.util.*

data class Person(var height: Int, var cnt: Int)

fun main() = with(System.`in`.bufferedReader()) {
    val N = readLine().toInt()

    val heights = Array<Int>(N) { 0 }
    for (i in heights.indices) {
        heights[i] = readLine().toInt()
    }

    val stack = Stack<Person>()

    var answer = 0L

    // 내림차순으로만 스택에 남긴다
    for (height in heights) {
        val now = Person(height, 1)

        // 자기보다 작거나 같은 것들을 스택에서 pop 하면서 센다. 얘들은 모두 나를 볼 수 있다.
        while (!stack.isEmpty() && stack.peek().height <= height) {
            val top = stack.pop()

            answer += top.cnt

            // 같은 경우에 스택에 넣을 카운트 증가시키기
            if (top.height == height) {
                now.cnt += top.cnt
            }
        }

        // 자기보다 큰 것이 스택에 남아 있다면 그 중 맨 오른쪽 하나만 나를 볼 수 있다
        if (!stack.isEmpty()) {
            answer++
        }

        stack.push(now)
    }

    println(answer)

}