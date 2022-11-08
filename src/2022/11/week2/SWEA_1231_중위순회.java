import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static final int MAX_V = 100;
    static final int NO_CHILD = 0;

    static StringBuilder sb = new StringBuilder();
    static int V;
    static char[] value = new char[MAX_V + 1];
    static int[] leftChild = new int[MAX_V + 1];
    static int[] rightChild = new int[MAX_V + 1];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        final int TESTS = 10;
        for (int tc = 1; tc <= TESTS; tc++) {
            // 메모리 초기화
            initMemory();

            // 정점 수 입력
            V = Integer.parseInt(br.readLine());

            // 각 정점의 정보 입력
            for (int i = 0; i < V; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                int node = Integer.parseInt(st.nextToken());
                char val = st.nextToken().charAt(0);
                int left = st.hasMoreTokens() ? Integer.parseInt(st.nextToken()) : NO_CHILD;
                int right = st.hasMoreTokens() ? Integer.parseInt(st.nextToken()) : NO_CHILD;

                value[node] = val;
                leftChild[node] = left;
                rightChild[node] = right;
            }

            // 중위 순회
            sb.append("#").append(tc).append(" ");
            printInorder(1);
            sb.append("\n");
        }

        System.out.print(sb.toString());

    } // end main

    public static void initMemory() {
        Arrays.fill(value, (char) 0);
        Arrays.fill(leftChild, NO_CHILD);
        Arrays.fill(rightChild, NO_CHILD);
    }

    public static void printInorder(int here) {
        if (leftChild[here] != NO_CHILD) {
            printInorder(leftChild[here]);
        }
        sb.append(value[here]);
        if (rightChild[here] != NO_CHILD) {
            printInorder(rightChild[here]);
        }
    }



}