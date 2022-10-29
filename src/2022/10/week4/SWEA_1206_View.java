import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static final int MAX_N = 1000, MAX_HEIGHT = 255;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        final int TESTS = 10;

        for (int tc = 1; tc <= TESTS; tc++) {
            // 건물 개수 입력
            int N = Integer.parseInt(br.readLine());

            // 건물의 높이들 입력
            int[] heights = new int[N];
            st = new StringTokenizer(br.readLine(), " ");
            for (int i = 0; i < N; i++) {
                heights[i] = Integer.parseInt(st.nextToken());
            }

            // 빌딩마다 조망권 확보된 세대 수 계산해서 누적
            int answer = 0;
            for (int myIdx = 0; myIdx < N; myIdx++) {
                int myHeight = heights[myIdx];

                int maxNeighborHeight = 0;
                for (int delta = -2; delta <= 2; delta++) {
                    int neighborIdx = myIdx + delta;
                    if (myIdx != neighborIdx && 0 <= neighborIdx && neighborIdx < N) {
                        maxNeighborHeight = Math.max(maxNeighborHeight, heights[neighborIdx]);
                    }
                }

                answer += Math.max(0, myHeight - maxNeighborHeight);
            }

            // 출력에 추가
            sb.append("#").append(tc).append(" ");
            sb.append(answer).append("\n");

        } // end for tc

        System.out.print(sb.toString());

    } // end main

}