import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static final int MAX_N = 100, MAX_MATRIX = 20;
    static final long INF = 987654321098765432L;
    static final long CACHE_EMPTY = -1L;

    static int N;
    static int[][] grid = new int[MAX_N][MAX_N];
    static boolean[][] isVisitedGrid = new boolean[MAX_N][MAX_N];

    static List<Matrix> matrixList = new ArrayList<>();
    static boolean[] isVisitedMatrix = new boolean[MAX_MATRIX];
    static Stack<Integer> stack = new Stack<>();

    static List<Matrix> orderedMatrixList = new ArrayList<>();
    static long[][] cache = new long[MAX_MATRIX][MAX_MATRIX];


    static class Matrix {
        int rows;
        int cols;

        public Matrix(int rows, int cols) {
            this.rows = rows;
            this.cols = cols;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        final int TESTS = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= TESTS; tc++) {
            initMemory();

            // 입력
            N = Integer.parseInt(br.readLine());
            for (int y = 0; y < N; y++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int x = 0; x < N; x++) {
                    grid[y][x] = Integer.parseInt(st.nextToken());
                }
            }

            // 행렬 찾기
            for (int y = 0; y < N; y++) {
                for (int x = 0; x < N; x++) {
                    if (!isVisitedGrid[y][x] && grid[y][x] != 0) {
                        Matrix matrix = findMatrix(y, x);
                        matrixList.add(matrix);
                    }
                }
            }

            // 곱셈 가능한 행렬 순서 구하기
            calcOrderedMatrixList();

            // 정답 계산
            long answer = getMinOperations(0, orderedMatrixList.size() - 1);

            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }

        System.out.print(sb.toString());

    } // end main

    /**
     * 테스트케이스마다 필요한 메모리 초기화
     */
    public static void initMemory() {
        for (int i = 0; i < grid.length; i++) {
            Arrays.fill(grid[i], 0);
        }
        for (int i = 0; i < isVisitedGrid.length; i++) {
            Arrays.fill(isVisitedGrid[i], false);
        }
        Arrays.fill(isVisitedMatrix, false);
        for (int i = 0; i < cache.length; i++) {
            Arrays.fill(cache[i], CACHE_EMPTY);
        }

        matrixList.clear();
        orderedMatrixList.clear();
        stack.clear();
    }

    /**
     * 좌상단 점 하나에서부터 시작하는 행렬 하나를 찾아서 리턴
     */
    public static Matrix findMatrix(int startY, int startX) {
        // 행렬 크기 구하기
        int rows = 0, cols = 0;
        for (int y = startY; y < N && grid[y][startX] != 0; y++) {
            rows++;
        }
        for (int x = startX; x < N && grid[startY][x] != 0; x++) {
            cols++;
        }

        // 방문 처리
        for (int dy = 0; dy < rows; dy++) {
            for (int dx = 0; dx < cols; dx++) {
                isVisitedGrid[startY + dy][startX + dx] = true;
            }
        }

        // 행렬 리턴
        return new Matrix(rows, cols);
    }

    /**
     * 행렬 순서 찾아서 orderedMatrixList 에 담기
     */
    public static void calcOrderedMatrixList() {
        for (int start = 0; start < matrixList.size(); start++) {
            Arrays.fill(isVisitedMatrix, false);
            stack.clear();
            if (dfs(start, 0)) {
                return;
            }
        }
    }

    /**
     * dfs 탐색을 통해 행렬 순서 찾기. 모든 정점 방문 성공 시 true, 아니면 false를 리턴.
     */
    public static boolean dfs(int here, int visitCnt) {
        // 방문
        if (isVisitedMatrix[here]) {
            return false;
        }
        isVisitedMatrix[here] = true;
        stack.push(here);

        // 모든 정점 방문 시
        if (visitCnt + 1 == matrixList.size()) {
            while (!stack.isEmpty()) {
                orderedMatrixList.add(matrixList.get(stack.pop()));
            }
            Collections.reverse(orderedMatrixList);
            return true;
        }

        // 다음 정점 탐색
        for (int next = 0; next < matrixList.size(); next++) {
            if (here != next && matrixList.get(here).cols == matrixList.get(next).rows) {
                if (dfs(next, visitCnt + 1)) {
                    return true;
                }
            }
        }

        // 방문 롤백
        isVisitedMatrix[here] = false;
        stack.pop();

        return false;
    }

    /**
     * orderedMatrixList의 start부터 end까지의 행렬을 곱하는 최소 연산 횟수를 구해서 리턴
     */
    public static long getMinOperations(int start, int end) {
        if (start - end == 0) {
            return 0L;
        }

        if (cache[start][end] != CACHE_EMPTY) {
            return cache[start][end];
        }

        long ret = INF;

        // 모든 i에 대해, start 부터 i까지 합치고, (i + 1) 부터 end 까지 합치고, 이 두 행렬을 합치는 것을 시도
        for (int i = start; i <= end - 1; i++) {
            long operations = getMinOperations(start, i) + getMinOperations(i + 1, end) + (long) (orderedMatrixList.get(start).rows * orderedMatrixList.get(i).cols * orderedMatrixList.get(end).cols);
            ret = Math.min(ret, operations);
        }

        return cache[start][end] = ret;

    }

}