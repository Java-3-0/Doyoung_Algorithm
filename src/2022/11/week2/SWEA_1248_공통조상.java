import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static final int MAX_V = 10000;
    static final int LOG_MAX_V = (int) (Math.ceil(Math.log(MAX_V) / Math.log(2)));

    static int V, E;
    static List<Integer>[] adjList = new ArrayList[MAX_V + 1];

    // parent[k][node]는 node 번 노드의 2^k 위 조상 노드 번호
    static int[][] parentST = new int[LOG_MAX_V + 1][MAX_V + 1];

    // depth[node]는 node 번 노드의 트리상에서의 깊이
    static int[] depth = new int[MAX_V + 1];
    static int[] subtreeSize = new int[MAX_V + 1];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        // adjList 메모리 할당
        for (int i = 0; i < adjList.length; i++) {
            adjList[i] = new ArrayList<Integer>();
        }

        final int TESTS = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= TESTS; tc++) {
            initMemory();

            // 정점 수 입력
            st = new StringTokenizer(br.readLine(), " ");
            V = Integer.parseInt(st.nextToken());
            E = Integer.parseInt(st.nextToken());
            int target1 = Integer.parseInt(st.nextToken());
            int target2 = Integer.parseInt(st.nextToken());

            // 간선 정보 입력
            st = new StringTokenizer(br.readLine(), " ");
            for (int i = 0; i < E; i++) {
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                adjList[from].add(to);
            }

            // dfs 하면서 depth 와 subtreeSize 계산하고 parentST의 첫 줄 설정
            depth[1] = 1;
            dfs(1);

            // sparseTable 생성
            makeParentSparseTable();

            // 최소 공통 조상과 그 서브트리 크기 구하기
            int lca = getLCA(target1, target2);
            int treeSize = subtreeSize[lca];

            // 출력에 추가
            sb.append("#").append(tc).append(" ").append(lca).append(" ").append(treeSize).append("\n");
        }

        // 출력
        System.out.print(sb.toString());

    } // end main

    public static void initMemory() {
        for (int i = 0; i < adjList.length; i++) {
            adjList[i].clear();
        }
        for (int i = 0; i < parentST.length; i++) {
            Arrays.fill(parentST[i], 0);
        }
        Arrays.fill(depth, 0);
        Arrays.fill(subtreeSize, 0);
    }

    // dfs 를 돌면서 트리 각 노드의 depth, subtreeSize 를 구하고 parentST의 첫 줄 설정 */
    public static void dfs(int now) {
        int size = 0;

        for (int next : adjList[now]) {
            parentST[0][next] = now;
            depth[next] = depth[now] + 1;
            dfs(next);
            size += subtreeSize[next];
        }

        subtreeSize[now] = size + 1;
    }

    // sparse table 만들기
    public static void makeParentSparseTable() {
        int len = parentST.length;
        for (int k = 1; k < len; k++) {
            for (int num = 1; num <= V; num++) {
                parentST[k][num] = parentST[k - 1][parentST[k - 1][num]];
            }
        }
    }

    // 두 노드의 최소 공통 조상을 구해서 리턴
    public static int getLCA(int node1, int node2) {
        int d1 = depth[node1];
        int d2 = depth[node2];

        // d1 > d2이면 d1 <= d2인 함수로 뒤집어서 호출
        if (d1 > d2) {
            return getLCA(node2, node1);
        }

        int diff = d2 - d1;
        // node2의 diff 번째 조상(node1과 같은 depth 인 것)을 구한다
        for (int k = LOG_MAX_V; k >= 0; k--) {
            if ((diff & (1 << k)) != 0) {
                node2 = parentST[k][node2];
            }
        }

        // 여기서 node1과 node2가 같아지면 여기가 루트이다
        if (node1 == node2) {
            return node1;
        }

        // 2^k 로 점프하면서 조상이 다를 때마다 a와 b를 위로 올린다
        for (int k = LOG_MAX_V; k >= 0; k--) {
            if (parentST[k][node1] != parentST[k][node2]) {
                node1 = parentST[k][node1];
                node2 = parentST[k][node2];
            }
        }

        // 마지막 상태에서 a와 b의 부모가 최소 공통 조상
        return parentST[0][node1];
    }

}