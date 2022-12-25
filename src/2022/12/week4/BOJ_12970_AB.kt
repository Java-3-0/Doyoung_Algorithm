fun main(): Unit = with(System.`in`.bufferedReader()) {
    val sb = StringBuilder()

    val (N, K) = readLine().split(" ").map { it.toInt() }

    // 반반 나눠야 최대한 많이 만들 수 있다
    val cntA = N / 2
    val cntB = N - cntA

    // 처음엔 0개인 상태로 초기화
    val list = mutableListOf<Char>()
    repeat(cntB) {
        list.add('B')
    }
    repeat(cntA) {
        list.add('A')
    }

    // 1개씩 늘려간다
    var cnt = 0
    while (cnt < K) {
        var found = false
        for (idx in list.size - 1 downTo 1) {
            // 맨 오른쪽 "BA"를 찾아서 뒤집는다
            if (list[idx - 1] == 'B' && list[idx] == 'A') {
                list[idx - 1] = 'A'
                list[idx] = 'B'

                cnt++
                found = true
                break
            }
        }

        if (!found) {
            break
        }
    }

    // 결과 출력
    if (cnt == K) {
        for (c in list) {
            sb.append(c)
        }
    } else {
        sb.append(-1)
    }

    println(sb)
}