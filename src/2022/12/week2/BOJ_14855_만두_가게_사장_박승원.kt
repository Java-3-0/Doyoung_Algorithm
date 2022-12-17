import java.util.*
import kotlin.math.max
import kotlin.math.min

const val MAX_INGREDIENTS = 1000
const val MAX_MENUS = 10
const val CACHE_EMPTY = -1

var INGREDIENTS = 0
var MENUS = 0
var C0 = 0
var D0 = 0

val menuList = mutableListOf<Menu>()
val cache = Array(MAX_MENUS + 1) { IntArray(MAX_INGREDIENTS + 1) { CACHE_EMPTY } }

data class Menu(val cnt: Int, val ingredient: Int, val price: Int)

fun main() = with(System.`in`.bufferedReader()) {
    val st = StringTokenizer(readLine(), " ")
    INGREDIENTS = st.nextToken().toInt()
    MENUS = st.nextToken().toInt()
    C0 = st.nextToken().toInt()
    D0 = st.nextToken().toInt()

    repeat(MENUS) {
        val (a, b, c, d) = readLine().split(" ").map { it.toInt() }
        menuList.add(Menu(a / b, c, d))
    }

    val answer = getMaxProfit(0, INGREDIENTS)

    println(answer)
}

fun getMaxProfit(startIdx: Int, remainingIngredient: Int): Int {
    // base case : 끝까지 간 경우 남은 걸로 스페셜 만두를 만든 이익을 리턴
    if (startIdx == MENUS) {
        return (remainingIngredient / C0) * D0
    }

    // memoization
    if (cache[startIdx][remainingIngredient] != CACHE_EMPTY) {
        return cache[startIdx][remainingIngredient]
    }

    // 새로 계산
    val menu = menuList[startIdx]
    val limitToMake = min(menu.cnt, remainingIngredient / menu.ingredient)

    var maxProfit = 0
    for (cnt in 0..limitToMake) {
        val profit = cnt * menu.price + getMaxProfit(startIdx + 1, remainingIngredient - cnt * menu.ingredient)
        maxProfit = max(maxProfit, profit)
    }

    cache[startIdx][remainingIngredient] = maxProfit
    return maxProfit
}