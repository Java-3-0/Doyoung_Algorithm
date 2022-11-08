import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static final int MAX_V = 1000;
    static final int NO_CHILD = 0;

    static StringBuilder sb = new StringBuilder();
    static int V;
    static String[] value = new String[MAX_V + 1];
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
                String val = st.nextToken();
                int left = st.hasMoreTokens() ? Integer.parseInt(st.nextToken()) : NO_CHILD;
                int right = st.hasMoreTokens() ? Integer.parseInt(st.nextToken()) : NO_CHILD;

                value[node] = val;
                leftChild[node] = left;
                rightChild[node] = right;
            }

            // 정답 계산 및 출력
            long answer = (long) Math.floor(getResult(1));
            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }

        System.out.print(sb.toString());

    } // end main

    public static void initMemory() {
        Arrays.fill(value, null);
        Arrays.fill(leftChild, NO_CHILD);
        Arrays.fill(rightChild, NO_CHILD);
    }

    public static double getResult(int here) {
        String val = value[here];
        if (val.charAt(0) < '0' || val.charAt(0) > '9') {
            double left = getResult(leftChild[here]);
            double right = getResult(rightChild[here]);

            double result = operation(left, right, val.charAt(0));
            return result;
        }
        else {
            double result = Double.parseDouble(val);
            return result;
        }
    }

    public static double operation(double a, double b, char op) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                return a / b;
            default:
                return -1.0;
        }
    }

}