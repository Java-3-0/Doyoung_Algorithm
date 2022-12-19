import java.util.*

const val MAX_N = 200000
const val MAX_M = 200000

var recipeIndexesUsingMaterial = Array(MAX_N + 1) { mutableListOf<Int>() }
val recipeList = mutableListOf<Recipe>()

data class Recipe(var materialSet: HashSet<Int>, var result: Int)

fun main() = with(System.`in`.bufferedReader()) {
    // 물약 개수, 레시피 개수 입력
    val (N, M) = readLine().split(" ").map { it.toInt() }

    for (i in 0 until M) {
        val st = StringTokenizer(readLine(), " ")
        val K = st.nextToken().toInt()

        val materialSet = HashSet<Int>()
        repeat(K) {
            val material = st.nextToken().toInt()
            materialSet.add(material)
            recipeIndexesUsingMaterial[material].add(i)
        }

        val result = st.nextToken().toInt()

        recipeList.add(Recipe(materialSet, result))
    }

    val L = readLine().toInt()
    val startList = readLine().split(" ").map { it.toInt() }.toHashSet()

    // 위상 정렬
    val answerSet = HashSet<Int>()
    val queue = LinkedList<Recipe>()

    // 시작 물약들 처리
    for (start in startList) {
        answerSet.add(start)

        for (recipeIdx in recipeIndexesUsingMaterial[start]) {
            val recipe = recipeList[recipeIdx]
            recipe.materialSet.remove(start)
            if (recipe.materialSet.isEmpty()) {
                queue.offer(recipe)
            }
        }
    }

    // 위상 정렬
    while (!queue.isEmpty()) {
        val now = queue.poll()!!
        val result = now.result
        // 새로 만들어진 물약이 있는 경우
        if (!answerSet.contains(result)) {
            answerSet.add(result)

            // 그 물약을 재료로 하는 레시피들 갱신
            for (recipeIdx in recipeIndexesUsingMaterial[result]) {
                val recipe = recipeList[recipeIdx]
                recipe.materialSet.remove(result)
                if (recipe.materialSet.isEmpty()) {
                    queue.offer(recipe)
                }
            }
        }
    }

    // 출력
    val sb = StringBuilder()
    val answerList = answerSet.toList().sorted()
    sb.append("${answerList.size}\n")
    for (answer in answerList) {
        sb.append("$answer ")
    }
    sb.append("\n")

    println(sb)

}