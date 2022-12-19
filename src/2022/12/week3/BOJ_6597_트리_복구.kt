import java.util.*

var sb = StringBuilder()

fun main() = with(System.`in`.bufferedReader()) {
    while (true) {
        val st = StringTokenizer(readLine() ?: break, " ")
        val preorder = st.nextToken()
        val inorder = st.nextToken()

        appendPostOrderToSB(preorder, inorder)
        sb.append("\n")

    }

    print(sb)

}

fun appendPostOrderToSB(preorder: String, inorder: String) {
    if (preorder.isEmpty()) {
        return
    }

    val root = preorder[0]

    val rootIdx = inorder.indexOf(root)
    val totalSize = preorder.length

    val leftSize = rootIdx

    appendPostOrderToSB(preorder.substring(1, 1 + leftSize), inorder.substring(0, 0 + leftSize))
    appendPostOrderToSB(preorder.substring(1 + leftSize, totalSize), inorder.substring(1 + leftSize, totalSize))
    sb.append(root)
}