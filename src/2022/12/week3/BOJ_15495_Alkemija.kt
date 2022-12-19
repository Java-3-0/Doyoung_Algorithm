import java.util.*

const val MAX_N = 100000
const val MAX_M = 100000

var recipeIndexesUsingMaterial = Array(MAX_N + 1) { mutableListOf<Int>() }

data class Recipe(var materialSet: HashSet<Int>, var resultSet: HashSet<Int>)

fun main() = with(System.`in`.bufferedReader()) {
    // 입력
    val (N, M) = readLine().split(" ").map { it.toInt() }

    val startSet = readLine().split(" ").map { it.toInt() }.toHashSet()

    val K = readLine().toInt()
    val recipeList = mutableListOf<Recipe>()
    for (i in 0 until K) {
        val (L, R) = readLine().split(" ").map { it.toInt() }
        val materialSet = readLine().split(" ").map { it.toInt() }.toHashSet()
        for (material in materialSet) {
            recipeIndexesUsingMaterial[material].add(i)
        }

        val resultSet = readLine().split(" ").map { it.toInt() }.toHashSet()

        recipeList.add(Recipe(materialSet, resultSet))
    }

    // 위상 정렬
    val answerSet = HashSet<Int>()
    val queue = LinkedList<Recipe>()

    // 시작 물질들 처리
    for (start in startSet) {
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
        for (result in now.resultSet) {
            // 새로 만들어진 물질이 있는 경우
            if (!answerSet.contains(result)) {
                answerSet.add(result)

                // 그 물질을 재료로 하는 레시피들 갱신
                for (recipeIdx in recipeIndexesUsingMaterial[result]) {
                    val recipe = recipeList[recipeIdx]
                    recipe.materialSet.remove(result)
                    if (recipe.materialSet.isEmpty()) {
                        queue.offer(recipe)
                    }
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